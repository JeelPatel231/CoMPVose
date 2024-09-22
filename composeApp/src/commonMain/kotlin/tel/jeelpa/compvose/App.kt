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

@Composable
@Preview
fun App() {

    val player = MPVWrapper()
    val mediaItem = object: MediaItem {
        override fun getUrl() = "https://raw.githubusercontent.com/rafaelreis-hotmart/Audio-Sample-files/refs/heads/master/sample.mp3"
    }

    player.setMediaItem(mediaItem)
    val events = player.getEventEmitter()

    MaterialTheme {
        val isPlaying by events.isPlaying.collectAsState()

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                if (isPlaying) player.pause() else player.play()
            }) {
                val text = if(isPlaying) "Playing" else "Paused"
                Text(text)
            }
        }
    }
}