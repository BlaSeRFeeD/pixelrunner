package com.example.pixelrunner.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.example.pixelrunner.ui.theme.PixelRunnerTheme

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PixelRunnerTheme {
                Text("Игра загружается...")
            }
        }
    }
}