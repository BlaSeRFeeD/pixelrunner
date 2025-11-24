package com.example.pixelrunner.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pixelrunner.R
import com.example.pixelrunner.data.MusicManager
import com.example.pixelrunner.ui.theme.PixelRunnerTheme

class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation =
            android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE // Горизонтальная ориентация

        MusicManager.start(this, R.raw.background_music)

        setContent {
            PixelRunnerTheme {
                MenuScreen(
                    onPlay = {
                        val intent = Intent(this, GameActivity::class.java)
                        startActivity(intent)
                    },
                    onSettings = {
                        val intent = Intent(this, SettingsActivity::class.java)
                        startActivity(intent)
                    },
                    onExit = {
                        finishAffinity()
                    }
                )
            }
        }
    }

    override fun onPause() {
        super.onPause()
        MusicManager.pause()
    }

    override fun onResume() {
        super.onResume()
        MusicManager.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        MusicManager.stop()
    }
}

@Composable
fun MenuScreen(onPlay: () -> Unit, onSettings: () -> Unit, onExit: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Фон
        Image(
            painter = painterResource(id = R.drawable.n4x2_ee43_210428),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(text = "Pixel Runner", fontSize = 32.sp)
            Button(onClick = onPlay) {
                Text("Play")
            }
            Button(onClick = onSettings) {
                Text("Settings")
            }
            Button(onClick = onExit) {
                Text("Exit")
            }
        }
    }
}