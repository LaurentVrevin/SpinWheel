package com.laurentvrevin.spinwheel.presentation.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import kotlinx.coroutines.launch

import kotlin.random.Random

@Composable
fun SpinningWheel(
    items: List<String>,
    modifier: Modifier = Modifier
) {
    val anglePerItem = 360f / items.size
    var spinAngle by remember { mutableStateOf(0f) }
    val rotation = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    // Function "Run the wheel"
    suspend fun spinWheel() {
        val targetRotation = Random.nextInt(3, 7) * 360 + Random.nextInt(0, 360)
        rotation.animateTo(
            targetValue = targetRotation.toFloat(),
            animationSpec = tween(durationMillis = 3000, easing = FastOutSlowInEasing)
        )
        spinAngle = targetRotation % 360f
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.size(300.dp)) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val radius = size.minDimension / 2
                val centerX = size.width / 2
                val centerY = size.height / 2

                for (i in items.indices) {
                    val startAngle = i * anglePerItem
                    drawArc(
                        color = if (i % 2 == 0) Color(0xFFFB8C00) else Color(0xFFAB47BC),
                        startAngle = startAngle + rotation.value % 360,
                        sweepAngle = anglePerItem,
                        useCenter = true,
                        topLeft = Offset(centerX - radius, centerY - radius),
                        size = Size(radius * 2, radius * 2)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        // Button to run the wheel
        Button(onClick = {
            coroutineScope.launch {
                spinWheel()
            }
        }) {
            Text("Lancer la roue")
        }
    }
}