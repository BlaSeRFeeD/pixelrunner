package com.example.pixelrunner.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Alignment
import androidx.compose.animation.core.*
import androidx.compose.ui.unit.dp
import com.example.pixelrunner.R

@Composable
fun GameView(
    isJumping: Boolean = false,
    moveDirection: String? = null
) {
    // Анимация "покачивания" при стоянии
    val idleTransition = rememberInfiniteTransition(label = "idle")
    val idleOffsetY by idleTransition.animateFloat(
        initialValue = 0f,
        targetValue = -10f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "idle_float"
    )

    // Анимация прыжка
    val jumpOffset by animateFloatAsState(
        targetValue = if (isJumping) -150f else 0f,
        animationSpec = tween(durationMillis = 500, easing = EaseOutQuad),
        label = "jump"
    )

    // Анимация движения влево/вправо
    var positionX by remember { mutableStateOf(60f) }
    LaunchedEffect(moveDirection) {
        when (moveDirection) {
            "left" -> positionX -= 15f
            "right" -> positionX += 15f
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Фон
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Персонаж
        Image(
            painter = painterResource(id = R.drawable.player),
            contentDescription = "Player",
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.BottomStart)
                .offset(x = positionX.dp, y = (idleOffsetY + jumpOffset).dp)
        )
    }
}