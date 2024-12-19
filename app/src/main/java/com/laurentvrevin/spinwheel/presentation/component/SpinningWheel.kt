package com.laurentvrevin.spinwheel.presentation.component

import android.content.Context
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.*
import com.laurentvrevin.spinwheel.utils.drawSpinningWheel
import com.laurentvrevin.spinwheel.utils.spinWheel
import kotlinx.coroutines.launch

@Composable
fun SpinningWheel(
    items: List<String>,
    modifier: Modifier = Modifier,
    context: Context
) {
    val anglePerItem = 360f / items.size
    val rotation = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf("") }
    val hapticFeedback = LocalHapticFeedback.current

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (selectedItem.isNotEmpty()) {
            Text("Selected Item : $selectedItem", style = MaterialTheme.typography.headlineMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.size(300.dp)) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawSpinningWheel(
                    items = items,
                    anglePerItem = anglePerItem,
                    rotationValue = rotation.value
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        PressableCTAButton(
            text = "Run",
            onSpinRequested = { pressDuration ->
                coroutineScope.launch {
                    spinWheel(
                        rotation = rotation,
                        anglePerItem = anglePerItem,
                        items = items,
                        impulseDuration = pressDuration,
                        onSpinCompleted = { selected ->
                            selectedItem = selected
                        },
                        hapticFeedback = hapticFeedback
                    )
                }
            }
        )
    }
}
