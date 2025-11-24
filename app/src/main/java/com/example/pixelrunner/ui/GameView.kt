package com.example.pixelrunner.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pixelrunner.R
import kotlinx.coroutines.delay

@Composable
fun GameView(
    moveLeft: Boolean,
    moveRight: Boolean,
    jumpRequested: Boolean,
    onJumpConsumed: () -> Unit,
    attackRequested: Boolean,
    onAttackConsumed: () -> Unit,
    restartRequested: Boolean,
    onRestartConsumed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val playerScreenX = 300f
    val playerWidth = 96f
    val platformHeight = 50f
    val bgWidth = 800f

    var worldX by remember { mutableStateOf(0f) }

    // Игрок
    var playerY by remember { mutableStateOf(platformHeight) }
    var playerVelY by remember { mutableStateOf(0f) }
    var isJumping by remember { mutableStateOf(false) }
    var playerHP by remember { mutableStateOf(3) }
    var isDead by remember { mutableStateOf(false) }
    var damageCooldown by remember { mutableStateOf(0f) }
    var playerFacingRight by remember { mutableStateOf(true) }

    // Враг (NPC)
    var enemyX by remember { mutableStateOf(900f) }
    var enemyY by remember { mutableStateOf(platformHeight) }
    var enemyHP by remember { mutableStateOf(3) }
    var enemyFacingRight by remember { mutableStateOf(true) }
    var enemyMovingRight by remember { mutableStateOf(true) }
    val enemyMinX = 800f
    val enemyMaxX = 1200f
    val enemySpeed = 100f

    val gravity = 2000f
    val moveSpeed = 400f
    val jumpImpulse = 850f

    Box(modifier = modifier.fillMaxSize()) {

        // Бесконечный фон
        for (i in -1..1) {
            Image(
                painter = painterResource(R.drawable.background),
                contentDescription = null,
                modifier = Modifier
                    .height(600.dp)
                    .width(bgWidth.dp)
                    .offset(x = (worldX + i * bgWidth).dp),
                contentScale = ContentScale.FillHeight
            )
        }

        // Бесконечная платформа
        for (i in -1..1) {
            Canvas(
                modifier = Modifier
                    .height(platformHeight.dp)
                    .width(bgWidth.dp)
                    .align(Alignment.BottomStart)
                    .offset(x = (worldX + i * bgWidth).dp)
            ) {
                drawRect(Color(0xFF553311), size = size)
            }
        }

        // Логика игры
        LaunchedEffect(moveLeft, moveRight, jumpRequested, restartRequested, attackRequested) {
            val dtMs = 16L
            val dt = dtMs / 1000f

            while (true) {

                // RESET
                if (restartRequested) {
                    worldX = 0f
                    enemyX = 900f
                    enemyHP = 3
                    enemyY = platformHeight
                    playerY = platformHeight
                    playerVelY = 0f
                    isJumping = false
                    playerHP = 3
                    isDead = false
                    damageCooldown = 0f
                    playerFacingRight = true
                    enemyFacingRight = true
                    enemyMovingRight = true
                    onRestartConsumed()
                }

                // Движение мира
                var velX = 0f
                if (!isDead) {
                    when {
                        moveRight && !moveLeft -> {
                            velX = -moveSpeed
                            playerFacingRight = true
                        }
                        moveLeft && !moveRight -> {
                            velX = moveSpeed
                            playerFacingRight = false
                        }
                    }
                }
                worldX += velX * dt

                // Loop фон
                if (worldX <= -bgWidth) worldX += bgWidth
                if (worldX >= bgWidth) worldX -= bgWidth

                // Прыжок
                if (jumpRequested && !isJumping && !isDead) {
                    playerVelY = jumpImpulse
                    isJumping = true
                    onJumpConsumed()
                }

                // Физика игрока
                playerY += playerVelY * dt
                playerVelY -= gravity * dt
                if (playerY < platformHeight) {
                    playerY = platformHeight
                    playerVelY = 0f
                    isJumping = false
                }

                // --- NPC враг: патрулирование
                if (enemyMovingRight) {
                    enemyX += enemySpeed * dt
                    enemyFacingRight = true
                    if (enemyX >= enemyMaxX) enemyMovingRight = false
                } else {
                    enemyX -= enemySpeed * dt
                    enemyFacingRight = false
                    if (enemyX <= enemyMinX) enemyMovingRight = true
                }

                val enemyScreenX = enemyX - (-worldX) + playerScreenX

                // Столкновение игрока с врагом
                if (!isDead &&
                    enemyHP > 0 &&
                    damageCooldown <= 0f &&
                    playerScreenX + playerWidth > enemyScreenX &&
                    playerScreenX < enemyScreenX + 96f &&
                    playerY < platformHeight + 100f
                ) {
                    playerHP--
                    damageCooldown = 1f
                }

                // Смерть игрока
                if (playerHP <= 0 && !isDead) {
                    isDead = true
                    playerVelY = -200f
                }

                // Игрок наносит урон врагу
                if (attackRequested &&
                    enemyHP > 0 &&
                    playerScreenX + 80f > enemyX - (-worldX) &&
                    playerScreenX < enemyX - (-worldX) + 80f
                ) {
                    enemyHP--
                    onAttackConsumed()
                }

                // Респавн врага
                if (enemyHP <= 0) {
                    enemyX = enemyMinX + 50f
                    enemyHP = 3
                }

                // Кулдаун
                if (damageCooldown > 0f) damageCooldown -= dt

                delay(dtMs)
            }
        }

        // UI: HP игрока
        Text(
            text = "HP: $playerHP",
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp),
            color = Color.White
        )

        // Игрок
        Image(
            painter = painterResource(R.drawable.player),
            contentDescription = null,
            modifier = Modifier
                .size(96.dp)
                .align(Alignment.BottomStart)
                .offset(x = playerScreenX.dp, y = (-playerY).dp)
                .graphicsLayer(scaleX = if (playerFacingRight) 1f else -1f)
        )

        // Враг (NPC)
        val enemyScreenX = enemyX - (-worldX) + playerScreenX
        Image(
            painter = painterResource(R.drawable.enemy_sprite),
            contentDescription = "Enemy",
            modifier = Modifier
                .size(96.dp)
                .align(Alignment.BottomStart)
                .offset(x = enemyScreenX.dp, y = (-enemyY).dp)
                .graphicsLayer(scaleX = if (enemyFacingRight) 1f else -1f),
            contentScale = ContentScale.FillBounds
        )
    }
}