package com.example.pixelrunner.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pixelrunner.R

@Composable
fun ControlButtons(
    onMoveLeft: () -> Unit,
    onMoveRight: () -> Unit,
    onJump: () -> Unit,
    onAttack: () -> Unit,
    onAction: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

        // 🎮 Левая зона — два стика рядом
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(30.dp) // расстояние между ними
        ) {
            // Левый стик
            Image(
                painter = painterResource(id = R.drawable.joystick_left),
                contentDescription = "Left Joystick",
                modifier = Modifier
                    .size(50.dp)
                    .clickable { onMoveLeft() }
            )

            // Правый стик (рядом)
            Image(
                painter = painterResource(id = R.drawable.joystick_right),
                contentDescription = "Right Joystick",
                modifier = Modifier
                    .size(50.dp)
                    .clickable { onMoveRight() }
            )
        }

        // ⬆️ Прыжок — справа внизу
        Image(
            painter = painterResource(id = R.drawable.ic_jump),
            contentDescription = "Jump",
            modifier = Modifier
                .size(90.dp)
                .align(Alignment.BottomEnd)
                .padding(end = 30.dp, bottom = 40.dp)
                .clickable { onJump() }
        )

        // ⚔️ Удар — выше прыжка
        Image(
            painter = painterResource(id = R.drawable.ic_attack),
            contentDescription = "Attack",
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.CenterEnd)
                .padding(end = 40.dp)
                .clickable { onAttack() }
        )

        // 🖐 Взаимодействие — левее центра
        Image(
            painter = painterResource(id = R.drawable.ic_action),
            contentDescription = "Action",
            modifier = Modifier
                .size(70.dp)
                .align(Alignment.CenterStart)
                .padding(start = 40.dp)
                .clickable { onAction() }
        )
    }
}