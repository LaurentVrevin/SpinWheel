package com.laurentvrevin.spinwheel.presentation.component
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
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random
@Composable
fun SpinningWheel(
    items: List<String>,
    modifier: Modifier = Modifier
) {
    val anglePerItem = 360f / items.size // Angle pour chaque section
    val rotation = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf("") }


    suspend fun spinWheel() {
        val targetRotation = Random.nextInt(3, 7) * 360 + Random.nextInt(0, 360)
        rotation.animateTo(
            targetValue = targetRotation.toFloat(),
            animationSpec = tween(durationMillis = 3000, easing = FastOutSlowInEasing)
        )

        val finalRotation = targetRotation % 360f
        val adjustedRotation = (360f - finalRotation + 270f) % 360f
        val selectedIndex = (adjustedRotation / anglePerItem).toInt() % items.size
        selectedItem = items[selectedIndex]
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.size(300.dp)) {

            Canvas(modifier = Modifier.fillMaxSize()) {
                val radius = size.minDimension / 2
                val centerX = size.width / 2
                val centerY = size.height / 2
                for (i in items.indices) {

                    val startAngle = i * anglePerItem + rotation.value

                    drawArc(
                        color = if (i % 2 == 0) Color(0xFFFB8C00) else Color(0xFFAB47BC),
                        startAngle = startAngle,
                        sweepAngle = anglePerItem,
                        useCenter = true,
                        size = Size(radius * 2, radius * 2),
                        topLeft = Offset(centerX - radius, centerY - radius)
                    )
                    //Put the text to center of the "Camembert" ;)
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
                                color = android.graphics.Color.BLACK
                                textSize = 36f
                                isAntiAlias = true
                            }
                        )
                    }
                }
            // To select item when wheel is stopping
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

        Button(onClick = {
            coroutineScope.launch {
                spinWheel()
            }
        }) {
            Text("Lancer la roue")
        }
        // Show selected item
        if (selectedItem.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Selected Item : $selectedItem", style = MaterialTheme.typography.headlineMedium)
        }
    }
}

