package com.laurentvrevin.spinwheel.presentation.screen

import com.laurentvrevin.spinwheel.presentation.component.SpinningWheel
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.laurentvrevin.spinwheel.data.getDefaultWheelItems

@Composable
fun MyAppScreen() {
    MaterialTheme {

        val items = getDefaultWheelItems()
        SpinningWheel(
            items = items,
            context = LocalContext.current
        )
    }
}