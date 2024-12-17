package com.laurentvrevin.spinwheel.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

fun vibrateLightly(context: Context) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    val duration = 50L // Base duration for vibration

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val amplitude = (100..255).random() // Randomize amplitude for variety
        vibrator.vibrate(VibrationEffect.createOneShot(duration, amplitude))
    } else {
        @Suppress("DEPRECATION")
        vibrator.vibrate(duration)
    }
}