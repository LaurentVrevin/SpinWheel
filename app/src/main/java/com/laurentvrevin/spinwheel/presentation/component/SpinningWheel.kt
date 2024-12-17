package com.laurentvrevin.spinwheel.presentation.component

import android.content.Context
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.*
import com.laurentvrevin.spinwheel.utils.vibrateLightly
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun SpinningWheel(
    items: List<String>,
    modifier: Modifier = Modifier,
    context: Context
) {
    // Initialize rotation animation and selected item state
    val anglePerItem = 360f / items.size
    val rotation = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf("") }

    // Function to animate the spinning of the wheel
    suspend fun spinWheel(impulseDuration: Long) {
        val impulseFactor = (impulseDuration / 100f).coerceIn(1f, 10f)
        val targetRotation = (impulseFactor * 360) + Random.nextInt(0, 360)

        rotation.animateTo(
            targetValue = rotation.value + targetRotation,
            animationSpec = tween(
                durationMillis = (2000 + (impulseFactor * 100)).toInt(),
                easing = FastOutSlowInEasing
            ),
            block = {
                // Trigger vibration periodically during the rotation
                if (value.toInt() % 30 == 0) { // Every ~30 degrees
                    vibrateLightly(context)
                }
            }
        )

        // Determine the selected item after the wheel stops
        val finalRotation = (rotation.value % 360f + 360f) % 360f
        val adjustedRotation = (360f - finalRotation + 270f) % 360f
        val selectedIndex = (adjustedRotation / anglePerItem).toInt() % items.size
        selectedItem = items[selectedIndex]
    }

    // Layout for the spinning wheel and the button
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display the selected item
        if (selectedItem.isNotEmpty()) {
            Text("Selected Item : $selectedItem", style = MaterialTheme.typography.headlineMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Draw the spinning wheel
        Box(modifier = Modifier.size(300.dp)) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val radius = size.minDimension / 2
                val centerX = size.width / 2
                val centerY = size.height / 2

                for (i in items.indices) {
                    // Draw each segment of the wheel
                    val startAngle = i * anglePerItem + rotation.value

                    drawArc(
                        color = if (i % 2 == 0) Color(0xFFFB8C00) else Color(0xFFAB47BC),
                        startAngle = startAngle,
                        sweepAngle = anglePerItem,
                        useCenter = true,
                        size = Size(radius * 2, radius * 2),
                        topLeft = Offset(centerX - radius, centerY - radius)
                    )

                    // Add text to the center of each segment
                    val middleAngle = startAngle + anglePerItem / 2
                    val textRadius = radius * 0.6f
                    val textX = centerX + textRadius * cos(Math.toRadians(middleAngle.toDouble())).toFloat()
                    val textY = centerY + textRadius * sin(Math.toRadians(middleAngle.toDouble())).toFloat()

                    withTransform({
                        rotate(middleAngle, Offset(textX, textY))
                    }) {
                        drawContext.canvas.nativeCanvas.drawText(
                            items[i],
                            textX,
                            textY,
                            android.graphics.Paint().apply {
                                textAlign = android.graphics.Paint.Align.CENTER
                                color = android.graphics.Color.WHITE
                                textSize = 36f
                                isAntiAlias = true
                            }
                        )
                    }
                }

                // Draw a fixed red arrow to indicate the selected item
                drawPath(
                    path = Path().apply {
                        moveTo(centerX, centerY - radius - 20)
                        lineTo(centerX - 20, centerY - radius + 10)
                        lineTo(centerX + 20, centerY - radius + 10)
                        close()
                    },
                    color = Color.Red
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add a button to spin the wheel
        PressableCTAButton(
            text = "Run",
            onSpinRequested = { pressDuration ->
                coroutineScope.launch {
                    spinWheel(pressDuration)
                }
            }
        )
    }
}
