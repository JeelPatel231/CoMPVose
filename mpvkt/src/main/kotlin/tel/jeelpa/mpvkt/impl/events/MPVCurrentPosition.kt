package tel.jeelpa.mpvkt.impl.events

import tel.jeelpa.mpvkt.impl.MPVThinAbstraction
import tel.jeelpa.mpvkt.native.MPVEvent
import tel.jeelpa.mpvkt.native.MPVEventId
import tel.jeelpa.mpvkt.native.MPVEventProperty
import tel.jeelpa.mpvkt.native.MPVFormat
import kotlin.math.floor
import kotlin.time.*

class CurrentPositionEventObserver(
    private val mpv: MPVThinAbstraction,
    private val callback: (String) -> Unit,
): MPVEventObserver {
    override fun init() {
        mpv.observeProperty(0, "playback-time", MPVFormat.MPV_FORMAT_DOUBLE)
    }

    override suspend fun handleEvent(mpvEvent: MPVEvent) {
        // filter out unwanted events
        if (mpvEvent.event_id != MPVEventId.MPV_EVENT_PROPERTY_CHANGE) return
        val evtData = MPVEventProperty(mpvEvent.data!!)
        if (evtData.name != "playback-time") return



        val position = evtData.data?.getDouble(0) ?: 0.0
        val duration = floor(position)

        // handle the event
        callback("$duration seconds")
    }
}
