package tel.jeelpa.mpvkt.impl.events

import tel.jeelpa.mpvkt.native.MPVEvent

/*
* There are no cleanup functions as it may interfere with other observers
* by cleaning up property observers which others might be using
*
* eg:
* - PlayPauseListener is registered (observes "pause" property)
* - AnotherListener is registered (observes "pause" property)
* - AnotherListener is now cleaned up (removed "pause" property observer)
*
* result - PlayPauseListener is now Broken
*
* */
interface MPVEventObserver {
    fun init()
    suspend fun handleEvent(mpvEvent: MPVEvent)
}
