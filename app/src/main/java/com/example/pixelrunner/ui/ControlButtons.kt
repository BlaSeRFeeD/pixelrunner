package com.example.pixelrunner.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pixelrunner.R

@Composable
fun ControlButtons() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Левый джойстик
        Image(
            painter = painterResource(id = R.drawable.ic_joystick), // добавь свой файл, например joystick.png
            contentDescription = "Move",
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.BottomStart)
                .padding(20.dp)
        )

        // Прыжок
        Image(
            painter = painterResource(id = R.drawable.ic_jump), // кнопка прыжка
            contentDescription = "Jump",
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .clickable {
                    // TODO: добавить логику прыжка
                }
        )

        // Удар
        Image(
            painter = painterResource(id = R.drawable.ic_attack), // кнопка удара
            contentDescription = "Attack",
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.CenterEnd)
                .padding(end = 30.dp)
                .clickable {
                    // TODO: логика удара
                }
        )

        // Взаимодействие
        Image(
            painter = painterResource(id = R.drawable.ic_action), // кнопка взаимодействия
            contentDescription = "Action",
            modifier = Modifier
                .size(70.dp)
                .align(Alignment.CenterStart)
                .padding(start = 40.dp)
                .clickable {
                    // TODO: логика взаимодействия
                }
        )
    }
}