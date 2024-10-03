package tel.jeelpa.mpvkt

import com.sun.jna.Pointer
import com.sun.jna.ptr.IntByReference
import tel.jeelpa.mpvkt.impl.MPVThinAbstraction
import tel.jeelpa.mpvkt.native.MPV
import tel.jeelpa.mpvkt.native.MPVFormat

class MPVWrapper(
    private val nativeInstance: MPVThinAbstraction
): Player {

    fun init() {
        // Initialize the player
        nativeInstance.initialize()
        nativeInstance.setOptionString("vid", "no")

        // pause instantly after creation
        nativeInstance.setProperty("pause", MPVFormat.MPV_FORMAT_FLAG, MPV.TRUE_FLAG)
    }

    override fun play() {
        nativeInstance.setProperty("pause", MPVFormat.MPV_FORMAT_FLAG , MPV.FALSE_FLAG)
    }

    override fun pause() {
        nativeInstance.setProperty("pause", MPVFormat.MPV_FORMAT_FLAG, MPV.TRUE_FLAG)
    }

    override fun stop() {
        TODO("Not yet implemented")
    }

    override fun next() {
        nativeInstance.commandString("playlist-next")
    }

    override fun previous() {
        nativeInstance.commandString("playlist-prev")
    }

    override fun setMediaItem(item: MediaItem) {
        val url = item.getUrl()
        nativeInstance.commandString("loadfile $url")
    }

    override fun addMediaItem(item: MediaItem) {
        val url = item.getUrl()
        nativeInstance.commandString("loadfile $url append-play")
    }

    override fun getCurrentMediaItemIndex(): Long {
        return nativeInstance.getPropertyString("playlist-pos")!!.toLong()
    }

    override fun getEventEmitter(): PlayerEventEmitter {
        return MPVEventEmitter(nativeInstance)
    }

    override fun isPlaying(): Boolean {
        // why 3? because only 1 and 0 are valid values, 3 will always be overwritten.
        // if you see 3 or any other garbage value while debugging this. something went wrong.
        val flag: Pointer = IntByReference(3).pointer
        nativeInstance.getProperty("pause", MPVFormat.MPV_FORMAT_FLAG , flag)
        return flag.getInt(0) == 0
    }

    override fun getCurrentPosition(): String {
        return nativeInstance.getPropertyString("time-pos") ?: "Unknown"
    }

}

fun MPVThinAbstraction.wrapper() = MPVWrapper(this)