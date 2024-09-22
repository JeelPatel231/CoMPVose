package tel.jeelpa.mpvkt

interface MediaItem {
    fun getUrl(): String

    // TODO: add extra params like header or DRM key/license URL
}

interface Player {
    fun play()
    fun pause()
    fun stop()

    fun next()
    fun previous()

    fun setMediaItem(item: MediaItem)
    fun addMediaItem(item: MediaItem)
    fun getCurrentMediaItemIndex(): Long

    fun getEventEmitter(): PlayerEventEmitter
    fun isPlaying(): Boolean
}