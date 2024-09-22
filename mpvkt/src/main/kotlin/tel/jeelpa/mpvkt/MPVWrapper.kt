package tel.jeelpa.mpvkt

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import tel.jeelpa.mpvkt.native.*

class MPVWrapper: Player {
    companion object {
        private val INSTANCE = MPV.INSTANCE
        private val PTR = INSTANCE.mpv_create()


        // pause instantly after creation
        init {
            // Initialize the player
            INSTANCE.mpv_initialize(PTR)
            INSTANCE.mpv_set_option_string(PTR, "vid", "no")
            INSTANCE.mpv_set_property(PTR, "pause", MPVFormat.MPV_FORMAT_FLAG, MPV.TRUE_FLAG)


            INSTANCE.mpv_observe_property(PTR, 0, "pause", MPVFormat.MPV_FORMAT_FLAG)

            // start observing playback state

//            println(
//                INSTANCE.consumeString(
//                    INSTANCE.mpv_get_property_string(PTR, "command-list")!!
//                )
//            )
        }
    }

    private val _isPlaying = MutableStateFlow(false)
//    private val mpvEvents = MutableSharedFlow<MPVEvent>()


    init {
        // TODO: dont use global scope launch
        GlobalScope.launch {
            while(true) {
                val evt = INSTANCE.mpv_wait_event(PTR, 0.0)!!
//                mpvEvents.emit(evt)

                if (evt.event_id == MPVEventId.MPV_EVENT_PROPERTY_CHANGE) {
                    val evtData = MPVEventProperty(evt.data!!)
                    if (evtData.name == "pause") {
                        val isPlaying = evtData.data!!.getInt(0) == 0
                        _isPlaying.update { isPlaying }
                    }
                }
            }
        }
    }

    override fun play() {
        INSTANCE.mpv_set_property(PTR, "pause", MPVFormat.MPV_FORMAT_FLAG , MPV.FALSE_FLAG)
    }

    override fun pause() {
        INSTANCE.mpv_set_property(PTR, "pause", MPVFormat.MPV_FORMAT_FLAG, MPV.TRUE_FLAG)
    }

    override fun stop() {
        TODO("Not yet implemented")
    }

    override fun next() {
        INSTANCE.mpv_command_string(PTR, "playlist-next")
    }

    override fun previous() {
        INSTANCE.mpv_command_string(PTR, "playlist-prev")
    }

    override fun setMediaItem(item: MediaItem) {
        val url = item.getUrl()
        INSTANCE.mpv_command_string(PTR, "loadfile $url")
    }

    override fun addMediaItem(item: MediaItem) {
        val url = item.getUrl()
        INSTANCE.mpv_command_string(PTR, "loadfile $url append-play")
    }

    override fun getCurrentMediaItemIndex(): Long {
        return INSTANCE.consumeString(
            INSTANCE.mpv_get_property_string(PTR, "playlist-pos")!!
        ).toLong()
    }

    override fun getEventEmitter(): PlayerEventEmitter {
        return PlayerEventEmitter(
            isPlaying = _isPlaying.asStateFlow(),
        )
    }

    override fun isPlaying(): Boolean {
        TODO()
    }
}