package com.example.pixelrunner.ui

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import com.example.pixelrunner.R
import com.example.pixelrunner.data.MusicManager
import com.example.pixelrunner.ui.theme.PixelRunnerTheme

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        setContent {
            PixelRunnerTheme {
                SettingsScreen(onBack = { finish() })
            }
        }
    }
}

@Composable
fun SettingsScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("GameSettings", Context.MODE_PRIVATE)

    var soundEnabled by remember { mutableStateOf(prefs.getBoolean("sound", true)) }
    var musicEnabled by remember { mutableStateOf(prefs.getBoolean("music", true)) }

    // Если музыка включена при открытии настроек, запускаем её
    LaunchedEffect(Unit) {
        if (musicEnabled) {
            MusicManager.start(context, R.raw.background_music)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // Фон
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Фон",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Полупрозрачная подложка для читаемости
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
        )

        // Контент поверх фона
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Settings",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(24.dp))

            // 🔈 Звук
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Sound", color = MaterialTheme.colorScheme.onBackground)
                Switch(
                    checked = soundEnabled,
                    onCheckedChange = {
                        soundEnabled = it
                        prefs.edit().putBoolean("sound", it).apply()
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // 🎵 Музыка
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Music", color = MaterialTheme.colorScheme.onBackground)
                Switch(
                    checked = musicEnabled,
                    onCheckedChange = { enabled ->
                        musicEnabled = enabled
                        prefs.edit().putBoolean("music", enabled).apply()
                        if (enabled) {
                            MusicManager.start(context, R.raw.background_music)
                        } else {
                            MusicManager.stop()
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(32.dp))

            // ♻️ Сброс прогресса
            Button(
                onClick = {
                    prefs.edit().clear().apply()
                    soundEnabled = true
                    musicEnabled = true
                    MusicManager.start(context, R.raw.background_music)
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Reset progress", color = MaterialTheme.colorScheme.onError)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 🔙 Назад
            Button(onClick = onBack) {
                Text("Back")
            }
        }
    }
}