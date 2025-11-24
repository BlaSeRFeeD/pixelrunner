package com.example.pixelrunner

data class Enemy(
    var x: Float,
    var y: Float,
    var hp: Int = 50,
    val attackPower: Int = 10,
    var direction: Int = 1 // движение: 1 вправо, -1 влево
)