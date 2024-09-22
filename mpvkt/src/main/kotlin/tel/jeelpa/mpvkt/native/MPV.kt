package tel.jeelpa.mpvkt.native

import com.sun.jna.Callback
import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.Structure
import com.sun.jna.ptr.IntByReference

interface MPV : Library {
    fun mpv_client_api_version(): Long
    fun mpv_create(): Pointer
    fun mpv_initialize(handle: Pointer): Int
    fun mpv_command(handle: Pointer, args: Array<String?>?): Int
    fun mpv_command_string(handle: Pointer, args: String?): Int
    fun mpv_get_property_string(handle: Pointer, name: String?): Pointer?
    fun mpv_set_property_string(handle: Pointer, name: String?, data: String?): Int
    fun mpv_set_property(handle: Pointer, name: String?, format: Int, data: Pointer): Int
    fun mpv_set_option_string(handle: Pointer, name: String?, data: String?): Int
    fun mpv_free(data: Pointer?)
    fun mpv_set_option(handle: Pointer, name: String?, format: Int, data: Pointer?): Int

    fun mpv_observe_property(handle: Pointer, reply_user_data: Long, name: String, format: Int)

    fun mpv_wait_event(handle: Pointer, timeOut: Double): MPVEvent?
    fun mpv_request_event(handle: Pointer, event_id: Int, enable: Int): Int



    companion object {
        val INSTANCE: MPV = Native.load("mpv", MPV::class.java)

        /* MPV Flags, Pointer to Boolean  */
        val FALSE_FLAG: Pointer = IntByReference(0).pointer
        val TRUE_FLAG: Pointer = IntByReference(1).pointer
    }
}

fun MPV.consumeString(pointer: Pointer) : String {
    val str = pointer.getString(0)
    mpv_free(pointer)
    return str
}
