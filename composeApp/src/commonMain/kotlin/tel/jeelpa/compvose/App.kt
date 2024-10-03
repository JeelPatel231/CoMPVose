package tel.jeelpa.compvose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import compvose.composeapp.generated.resources.Res
import compvose.composeapp.generated.resources.compose_multiplatform
import tel.jeelpa.mpvkt.MPVWrapper
import tel.jeelpa.mpvkt.MediaItem
import tel.jeelpa.mpvkt.impl.MPVThinAbstraction
import tel.jeelpa.mpvkt.native.getNativeMPV
import tel.jeelpa.mpvkt.wrapper

@Composable
@Preview
fun App() {

    val thin = MPVThinAbstraction()
    val player = thin.wrapper().apply { init() }

    thin.setOptionString("ytdl", "yes")


    val playlist = listOf(
        "https://www.youtube.com/watch?v=KZPKQk4Im8c",
        "https://www.youtube.com/watch?v=Rwzy6Qt8gq8",
        "https://www.youtube.com/watch?v=6Jfd0Ar3luQ"
    ).map {
        object : MediaItem {
            override fun getUrl() = it
        }
    }

    playlist.onEach { player.addMediaItem(it) }
    val events = player.getEventEmitter()

    MaterialTheme {
        val isPlaying by events.isPlaying.collectAsState()
        val currentPosition by events.currentPosition.collectAsState()

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { player.previous() }) {
                Text("Previous")
            }
            Button(onClick = {
                if (isPlaying) player.pause() else player.play()
            }) {
                val text = if(isPlaying) "Playing" else "Paused"
                Text(text)
            }
            Button(onClick = { player.next() }) {
                Text("Next")
            }

            Text("Current Position : $currentPosition")
        }
    }
}