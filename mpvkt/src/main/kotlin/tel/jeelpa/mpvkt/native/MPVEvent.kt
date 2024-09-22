package tel.jeelpa.mpvkt.native

import com.sun.jna.Pointer
import com.sun.jna.Structure

object MPVEventId {
    const val MPV_EVENT_NONE              = 0
    const val MPV_EVENT_SHUTDOWN          = 1
    const val MPV_EVENT_LOG_MESSAGE       = 2
    const val MPV_EVENT_GET_PROPERTY_REPLY = 3
    const val MPV_EVENT_SET_PROPERTY_REPLY = 4
    const val MPV_EVENT_COMMAND_REPLY     = 5
    const val MPV_EVENT_START_FILE        = 6
    const val MPV_EVENT_END_FILE          = 7
    const val MPV_EVENT_FILE_LOADED       = 8

    /* DEPRECATED */ const val MPV_EVENT_IDLE              = 11
    /* DEPRECATED */ const val MPV_EVENT_TICK              = 14

    const val MPV_EVENT_CLIENT_MESSAGE    = 16
    const val MPV_EVENT_VIDEO_RECONFIG    = 17
    const val MPV_EVENT_AUDIO_RECONFIG    = 18
    const val MPV_EVENT_SEEK              = 20
    const val MPV_EVENT_PLAYBACK_RESTART  = 21
    const val MPV_EVENT_PROPERTY_CHANGE   = 22
    const val MPV_EVENT_QUEUE_OVERFLOW    = 24
    const val MPV_EVENT_HOOK              = 25
}

class MPVEvent : Structure() {
    @JvmField var event_id: Int = 0
    @JvmField var error: Int = 0
    @JvmField var reply_userdata: Long = 0
    @JvmField var data: Pointer? = null

    override fun getFieldOrder() =
        listOf("event_id", "error", "reply_userdata", "data")
}
