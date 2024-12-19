package com.laurentvrevin.spinwheel.utils

import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import kotlin.math.cos
import kotlin.math.sin

fun DrawScope.drawSpinningWheel(
    items: List<String>,
    anglePerItem: Float,
    rotationValue: Float
) {
    val radius = size.minDimension / 2
    val centerX = size.width / 2
    val centerY = size.height / 2

    // Dessiner les segments de la roue
    items.forEachIndexed { index, item ->
        val startAngle = index * anglePerItem + rotationValue

        // Dessiner le segment
        drawArc(
            color = if (index % 2 == 0) Color(0xFFFB8C00) else Color(0xFFAB47BC),
            startAngle = startAngle,
            sweepAngle = anglePerItem,
            useCenter = true,
            size = Size(radius * 2, radius * 2),
            topLeft = Offset(centerX - radius, centerY - radius)
        )

        // Ajouter le texte au centre du segment
        val middleAngle = startAngle + anglePerItem / 2
        val textRadius = radius * 0.6f
        val textX = centerX + textRadius * cos(Math.toRadians(middleAngle.toDouble())).toFloat()
        val textY = centerY + textRadius * sin(Math.toRadians(middleAngle.toDouble())).toFloat()

        // Dessiner le texte
        withTransform({
            rotate(middleAngle, Offset(textX, textY))
        }) {
            drawContext.canvas.nativeCanvas.drawText(
                item,
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

    // Calculer un décalage oscillant pour la flèche
    val arrowOffset = 10 * sin(Math.toRadians(rotationValue.toDouble())).toFloat()

    // Dessiner la flèche qui pointe vers le bas, située au-dessus de la roue
    drawPath(
        path = Path().apply {
            moveTo(centerX, centerY - radius - 10 + arrowOffset) // Base de la flèche (haut) avec décalage
            lineTo(centerX - 20, centerY - radius - 50 + arrowOffset) // Coin gauche avec décalage
            lineTo(centerX + 20, centerY - radius - 50 + arrowOffset) // Coin droit avec décalage
            close()
        },
        color = Color.Black
    )
}


