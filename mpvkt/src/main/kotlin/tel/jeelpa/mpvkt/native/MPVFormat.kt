package tel.jeelpa.mpvkt.native

import com.sun.jna.Pointer

object MPVFormat {
    const val MPV_FORMAT_NONE             = 0
    const val MPV_FORMAT_STRING           = 1
    const val MPV_FORMAT_OSD_STRING       = 2
    const val MPV_FORMAT_FLAG             = 3
    const val MPV_FORMAT_INT64            = 4
    const val MPV_FORMAT_DOUBLE           = 5
    const val MPV_FORMAT_NODE             = 6
    const val MPV_FORMAT_NODE_ARRAY       = 7
    const val MPV_FORMAT_NODE_MAP         = 8
    const val MPV_FORMAT_BYTE_ARRAY       = 9
}
