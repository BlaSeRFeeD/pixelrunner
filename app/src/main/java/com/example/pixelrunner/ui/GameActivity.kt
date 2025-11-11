package com.example.pixelrunner.ui

import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.pixelrunner.ui.theme.PixelRunnerTheme

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Принудительно включаем альбомную ориентацию
        requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        setContent {
            PixelRunnerTheme {
                GameScreen()
            }
        }
    }
}

@Composable
fun GameScreen() {
    var isJumping by remember { mutableStateOf(false) }
    var moveDirection by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope() // ✅ используем scope

    Box(modifier = Modifier.fillMaxSize()) {
        GameView(isJumping = isJumping, moveDirection = moveDirection)

        ControlButtons(
            onMoveLeft = { moveDirection = "left" },
            onMoveRight = { moveDirection = "right" },
            onJump = {
                isJumping = true
                scope.launch {
                    kotlinx.coroutines.delay(600)
                    isJumping = false
                }
            },
            onAttack = {
                // TODO: логика удара
            },
            onAction = {
                // TODO: логика взаимодействия
            }
        )
    }
}