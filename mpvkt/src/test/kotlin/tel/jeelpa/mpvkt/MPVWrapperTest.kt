package tel.jeelpa.mpvkt

import com.sun.jna.Pointer
import com.sun.jna.ptr.IntByReference
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import tel.jeelpa.mpvkt.native.MPV
import tel.jeelpa.mpvkt.native.consumeString


class MPVWrapperTest {
    companion object {
        val mpv = MPVWrapper()
        @JvmStatic
        fun main(args: Array<String>): Unit = runBlocking {
            val videoUrl = "https://raw.githubusercontent.com/rafaelreis-hotmart/Audio-Sample-files/refs/heads/master/sample.mp3"

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