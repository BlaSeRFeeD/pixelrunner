package com.example.pixelrunner.ui

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
    // Управление
    var moveLeft by remember { mutableStateOf(false) }
    var moveRight by remember { mutableStateOf(false) }
    var jumpRequested by remember { mutableStateOf(false) }
    var attackRequested by remember { mutableStateOf(false) }
    var actionRequested by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Игровая логика и рисование
        GameView(
            moveLeft = moveLeft,
            moveRight = moveRight,
            jumpRequested = jumpRequested,
            onJumpConsumed = { jumpRequested = false },
            attackRequested = attackRequested,
            onAttackConsumed = { attackRequested = false },
            restartRequested = actionRequested,
            onRestartConsumed = { actionRequested = false },
            modifier = Modifier.fillMaxSize()
        )

        // Контролы
        ControlButtons(
            onMoveLeftDown = { moveLeft = true },
            onMoveLeftUp = { moveLeft = false },
            onMoveRightDown = { moveRight = true },
            onMoveRightUp = { moveRight = false },
            onJump = { jumpRequested = true },
            onAttack = { attackRequested = true },
            onAction = { actionRequested = true }
        )
    }
}