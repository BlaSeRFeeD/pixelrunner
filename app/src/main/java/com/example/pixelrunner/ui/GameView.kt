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
fun GameView() {
    // Анимация — персонаж "дышит" (немного двигается вверх-вниз)
    val infiniteTransition = rememberInfiniteTransition(label = "character_float")
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -10f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Фон
        Image(
            painter = painterResource(id = R.drawable.backgroung),
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
                .offset(x = 60.dp, y = offsetY.dp)
        )
    }
}