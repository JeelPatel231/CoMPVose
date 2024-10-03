package tel.jeelpa.mpvkt

import tel.jeelpa.mpvkt.native.*


class MPVTest {
    companion object {
        val MPV_INSTANCE = getNativeMPV()
        val MPV_POINTER = MPV_INSTANCE.mpv_create()

        @JvmStatic
        fun main(args: Array<String>) {
            val videoUrl = Constants.videoUrl

            val t = Thread {
                while (true) {
                    val evt = MPV_INSTANCE.mpv_wait_event(MPV_POINTER, 0.0)!!
                    if(evt.event_id == MPVEventId.MPV_EVENT_PROPERTY_CHANGE) {
                        val evtData = MPVEventProperty(evt.data!!)
                        println( evtData.name )
                        println( evtData.data!!.getInt(0) == 1 )
                    }
                }
            }
            t.start()

            MPV_INSTANCE.mpv_observe_property(MPV_POINTER, 0, "pause", MPVFormat.MPV_FORMAT_FLAG)

            MPV_INSTANCE.mpv_initialize(MPV_POINTER)
            MPV_INSTANCE.mpv_set_option_string(MPV_POINTER, "vid", "no")

            MPV_INSTANCE.mpv_command_string(MPV_POINTER, "loadfile $videoUrl")

            Thread.sleep(3000)

            MPV_INSTANCE.mpv_set_property(MPV_POINTER, "pause", MPVFormat.MPV_FORMAT_FLAG, MPV.TRUE_FLAG)
            Thread.sleep(3000)

            MPV_INSTANCE.mpv_set_property(MPV_POINTER, "pause", MPVFormat.MPV_FORMAT_FLAG, MPV.FALSE_FLAG)
            t.join()
        }
    }
}