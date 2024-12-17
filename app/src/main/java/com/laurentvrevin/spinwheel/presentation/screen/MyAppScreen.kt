package com.laurentvrevin.spinwheel.presentation.screen

import com.laurentvrevin.spinwheel.presentation.component.SpinningWheel
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun MyAppScreen() {
    MaterialTheme {
        //For the moment, i make a fake list to test, i'll see later to add with a different screen
        val items = listOf("Raclette", "Crêpes salées", "Omelette", "Ratatouille", "Pizza", "Tartiflette")
        SpinningWheel(
            items = items,
            context = LocalContext.current
        )
    }
}