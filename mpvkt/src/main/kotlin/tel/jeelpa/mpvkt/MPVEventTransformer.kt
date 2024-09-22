package tel.jeelpa.mpvkt

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import tel.jeelpa.mpvkt.native.MPVEvent

class MPVPlaybackStateEventConsumer(
    private val sharedFlow: SharedFlow<MPVEvent>
) {
    val isPlaying = MutableStateFlow(false)
}