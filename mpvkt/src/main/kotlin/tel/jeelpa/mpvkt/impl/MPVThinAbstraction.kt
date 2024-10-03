package tel.jeelpa.mpvkt.impl

import com.sun.jna.Pointer
import kotlinx.coroutines.*
import tel.jeelpa.mpvkt.impl.events.MPVEventObserver
import tel.jeelpa.mpvkt.native.getNativeMPV

class MPVThinAbstraction {
    private val instance = getNativeMPV()
    private val pointer = instance.mpv_create()

    fun apiVersion() =
        instance.mpv_client_api_version()

    fun initialize() =
        instance.mpv_initialize(pointer)

    fun command(args: Array<String?> = emptyArray()) =
        instance.mpv_command(pointer, args)
    fun commandString(args: String?) =
        instance.mpv_command_string(pointer, args)

    fun getPropertyString(name: String?) =
        instance.mpv_get_property_string(pointer, name)

    fun setPropertyString(name: String?, data: String?) =
        instance.mpv_set_property_string(pointer, name, data)

    fun getProperty(name: String?, format: Int, data: Pointer) =
        instance.mpv_get_property(pointer, name, format, data)

    fun setProperty(name: String?, format: Int, data: Pointer) =
        instance.mpv_set_property(pointer, name, format, data)

    fun setOptionString(name: String?, data: String?) =
        instance.mpv_set_option_string(pointer, name, data)

    // mpv_free is used for more than instance itself, string and structs too
    fun free(data: Pointer?) =
        instance.mpv_free(data)

    fun setOption(name: String?, format: Int, data: Pointer?) =
        instance.mpv_set_option(pointer, name, format, data)

    fun observeProperty(reply_user_data: Long, name: String, format: Int) =
        instance.mpv_observe_property(pointer, reply_user_data, name, format)

    fun unobserveProperty(reply_user_data: Long, name: String, format: Int) =
        instance.mpv_unobserve_property(pointer, reply_user_data, name, format)

    fun waitEvent(timeOut: Double) =
        instance.mpv_wait_event(pointer, timeOut)

    fun requestEvent(event_id: Int, enable: Int) =
        instance.mpv_request_event(pointer, event_id, enable)


    /// extra abstractions
    private val eventHandlers = mutableListOf<MPVEventObserver>()

    fun addEventHandler(mpvEventObserver: MPVEventObserver) {
        mpvEventObserver.init()
        eventHandlers.add(mpvEventObserver)
    }

    fun removeEventHandler(mpvEventObserver: MPVEventObserver) =
        eventHandlers.remove(mpvEventObserver)

    // TODO: dont use global scope launch
    private val eventListenerThread = GlobalScope.launch {
        while (true) {
            val evt = instance.mpv_wait_event(pointer, 0.0)!!
            eventHandlers.onEach { it.handleEvent(evt) }
        }
    }

    fun dispose() {
        free(pointer)
        eventListenerThread.cancel("Disposed")
    }
}
