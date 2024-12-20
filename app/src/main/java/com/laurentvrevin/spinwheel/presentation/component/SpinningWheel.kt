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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.laurentvrevin.spinwheel.R
import com.laurentvrevin.spinwheel.utils.drawSpinningWheel
import com.laurentvrevin.spinwheel.utils.animateWheelSpin
import kotlinx.coroutines.launch

@Composable
fun SpinningWheel(
    items: MutableList<String>,
    modifier: Modifier = Modifier,
    context: Context
) {
    //Add an element if the list size is odd so that it is always even
    if (items.size % 2 != 0) {
        items.add(context.getString(R.string.fallback_item))
    }
    val anglePerItem = 360f / items.size

    val rotation = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf("") }
    val hapticFeedback = LocalHapticFeedback.current

    Column(modifier = modifier
        .fillMaxWidth()
        .padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.title_text),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(selectedItem, style = MaterialTheme.typography.headlineMedium)
    }

    Spacer(modifier = Modifier.height(16.dp))

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(modifier = Modifier.size(300.dp)) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawSpinningWheel(
                    items = items,
                    anglePerItem = anglePerItem,
                    rotationValue = rotation.value
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        PressableCTAButton(
            text = "Run",
            onSpinRequested = { pressDuration ->
                coroutineScope.launch {
                    animateWheelSpin(
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

