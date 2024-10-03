package tel.jeelpa.mpvkt

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import tel.jeelpa.mpvkt.impl.MPVThinAbstraction
import tel.jeelpa.mpvkt.native.getNativeMPV


class MPVWrapperTest {
    companion object {
        val mpv = MPVThinAbstraction().wrapper().apply { init() }
        @JvmStatic
        fun main(args: Array<String>): Unit = runBlocking {
            val videoUrl = Constants.videoUrl

            val mediaItem = object : MediaItem {
                override fun getUrl(): String = videoUrl
            }

            val t = launch {
                val eventEmitter = mpv.getEventEmitter()
                eventEmitter.isPlaying.collect {
                    println("PLAYER isPlaying : $it")
                }
            }
            t.start()
            mpv.setMediaItem(mediaItem)
            mpv.play()
            delay(3000)
            mpv.pause()
            delay(3000)
            mpv.play()
            delay(3000)
            mpv.pause()
        }
    }
}