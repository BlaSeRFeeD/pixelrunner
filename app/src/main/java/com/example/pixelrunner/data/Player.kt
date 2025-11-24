package com.example.pixelrunner

data class Player(
    var x: Float,
    var y: Float,
    var hp: Int = 100,
    var score: Int = 0,
    val attackPower: Int = 25,
    var isJumping: Boolean = false,
    var velocityY: Float = 0f,
    var isAttacking: Boolean = false
)