package com.example.pixelrunner.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.pixelrunner.R

@Composable
fun ControlButtons(
    onMoveLeftDown: () -> Unit,
    onMoveLeftUp: () -> Unit,
    onMoveRightDown: () -> Unit,
    onMoveRightUp: () -> Unit,
    onJump: () -> Unit,
    onAttack: () -> Unit,
    onAction: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .align(Alignment.BottomStart),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Влево
                Image(
                    painter = painterResource(R.drawable.joystick_left),
                    contentDescription = "Left",
                    modifier = Modifier
                        .size(120.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    onMoveLeftDown()
                                    tryAwaitRelease()
                                    onMoveLeftUp()
                                }
                            )
                        }
                )
                Spacer(Modifier.width(24.dp))
                // Вправо
                Image(
                    painter = painterResource(R.drawable.joystick_right),
                    contentDescription = "Right",
                    modifier = Modifier
                        .size(120.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    onMoveRightDown()
                                    tryAwaitRelease()
                                    onMoveRightUp()
                                }
                            )
                        }
                )
            }

            // Кнопки справа: прыжок, атака, действие
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                Image(
                    painter = painterResource(R.drawable.ic_jump),
                    contentDescription = "Jump",
                    modifier = Modifier
                        .size(100.dp)
                        .clickable { onJump() }
                )
                Image(
                    painter = painterResource(R.drawable.ic_attack),
                    contentDescription = "Attack",
                    modifier = Modifier
                        .size(100.dp)
                        .clickable { onAttack() }
                )
                Image(
                    painter = painterResource(R.drawable.ic_action),
                    contentDescription = "Action",
                    modifier = Modifier
                        .size(100.dp)
                        .clickable { onAction() }
                )
            }
        }
    }
}