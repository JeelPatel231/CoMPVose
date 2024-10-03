package tel.jeelpa.mpvkt.impl.events

import tel.jeelpa.mpvkt.impl.MPVThinAbstraction
import tel.jeelpa.mpvkt.native.MPVEvent
import tel.jeelpa.mpvkt.native.MPVEventId
import tel.jeelpa.mpvkt.native.MPVEventProperty
import tel.jeelpa.mpvkt.native.MPVFormat

class PlayPauseEventObserver(
    private val mpv: MPVThinAbstraction,
    private val callback: (Boolean) -> Unit,
): MPVEventObserver {
    override fun init() {
        mpv.observeProperty(0, "pause", MPVFormat.MPV_FORMAT_FLAG)
    }

    override suspend fun handleEvent(mpvEvent: MPVEvent) {
        // filter out unwanted events
        if (mpvEvent.event_id != MPVEventId.MPV_EVENT_PROPERTY_CHANGE) return
        val evtData = MPVEventProperty(mpvEvent.data!!)
        if (evtData.name != "pause") return
        val isPlaying = evtData.data!!.getInt(0) == 0

        // handle the event
        callback(isPlaying)
    }
}
