package tel.jeelpa.mpvkt

import kotlinx.coroutines.flow.StateFlow

class PlayerEventEmitter(
    val isPlaying: StateFlow<Boolean>,
//    val currentMediaItem: StateFlow<MediaItem>,
)