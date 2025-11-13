package com.example.pixelrunner.data

import android.content.Context
import android.media.MediaPlayer

object MusicManager {
    private var mediaPlayer: MediaPlayer? = null

    fun start(context: Context, musicResId: Int, loop: Boolean = true) {
        stop()
        mediaPlayer = MediaPlayer.create(context, musicResId)
        mediaPlayer?.isLooping = loop
        mediaPlayer?.start()
    }

    fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun pause() {
        mediaPlayer?.pause()
    }

    fun resume() {
        mediaPlayer?.start()
    }
}