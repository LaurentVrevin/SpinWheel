package com.laurentvrevin.spinwheel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import com.laurentvrevin.spinwheel.presentation.screen.MyAppScreen
import com.laurentvrevin.spinwheel.presentation.theme.SpinWheelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpinWheelTheme {
                MyAppScreen()
            }
        }
    }
}

