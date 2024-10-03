package tel.jeelpa.mpvkt

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import tel.jeelpa.mpvkt.impl.MPVThinAbstraction
import tel.jeelpa.mpvkt.native.MPV
import tel.jeelpa.mpvkt.native.MPVFormat

class MPVThinWrapperTest {
    companion object {
        @JvmStatic
        fun main(args: Array<String>): Unit = runBlocking {
            val videoUrl = Constants.videoUrl

            val nativeInstance = MPVThinAbstraction()

            val wrapper = nativeInstance.wrapper().apply { init() }
            val eventListener = MPVEventEmitter(nativeInstance)
            launch {
                eventListener.isPlaying.collect {
                    println(it)
                }
            }

            nativeInstance.commandString("loadfile $videoUrl")

            nativeInstance.setProperty("pause", MPVFormat.MPV_FORMAT_FLAG, MPV.FALSE_FLAG)
            delay(3000)

            nativeInstance.setProperty("pause", MPVFormat.MPV_FORMAT_FLAG, MPV.TRUE_FLAG)
            delay(3000)

            nativeInstance.setProperty("pause", MPVFormat.MPV_FORMAT_FLAG, MPV.FALSE_FLAG)
        }
    }
}