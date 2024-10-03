package tel.jeelpa.mpvkt

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import tel.jeelpa.mpvkt.impl.MPVThinAbstraction
import tel.jeelpa.mpvkt.impl.events.CurrentPositionEventObserver
import tel.jeelpa.mpvkt.impl.events.PlayPauseEventObserver

interface PlayerEventEmitter {
    val isPlaying: StateFlow<Boolean>
    val currentPosition: StateFlow<String>
//    val currentMediaItem: StateFlow<MediaItem>
}


class MPVEventEmitter(private val mpvInstance: MPVThinAbstraction): PlayerEventEmitter {
    private val wrapperAbstraction = mpvInstance.wrapper()

    private val _isPlaying = MutableStateFlow(wrapperAbstraction.isPlaying())
    override val isPlaying = _isPlaying.asStateFlow()

    private val _currentPosition = MutableStateFlow(wrapperAbstraction.getCurrentPosition())
    override val currentPosition = _currentPosition.asStateFlow()


    private val playPauseListener = PlayPauseEventObserver(mpvInstance) { newIsPlaying ->
        _isPlaying.update { newIsPlaying }
    }

    private val positionEventObserver = CurrentPositionEventObserver(mpvInstance) { newPos ->
        _currentPosition.update { newPos }
    }

    init {
        mpvInstance.addEventHandler(playPauseListener)
        mpvInstance.addEventHandler(positionEventObserver)
    }
}

