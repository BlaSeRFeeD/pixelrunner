package com.example.pixelrunner.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pixelrunner.ui.theme.PixelRunnerTheme

class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PixelRunnerTheme {
                MenuScreen(
                    onPlay = {
                        val intent = Intent(this, GameActivity::class.java)
                        startActivity(intent)
                    },
                    onContinue = {
                        val intent = Intent(this, GameActivity::class.java)
                        intent.putExtra("continue", true)
                        startActivity(intent)
                    },
                    onExit = {
                        finishAffinity()
                    }
                )
            }
        }
    }
}

@Composable
fun MenuScreen(onPlay: () -> Unit, onContinue: () -> Unit, onExit: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(text = "Pixel Runner", fontSize = 32.sp)
            Button(onClick = onPlay) {
                Text("Play")
            }
            Button(onClick = onContinue) {
                Text("Continue")
            }
            Button(onClick = onExit) {
                Text("Exit")
            }
        }
    }
}