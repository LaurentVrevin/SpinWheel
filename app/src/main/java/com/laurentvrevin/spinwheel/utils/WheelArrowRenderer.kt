package com.laurentvrevin.spinwheel.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope

// Draw the arrow pointing downwards
fun DrawScope.drawArrow(
    centerX: Float,
    centerY: Float,
    radius: Float,
    arrowOffset: Float
) {
    drawPath(
        path = Path().apply {
            moveTo(centerX, centerY - radius - 10 + arrowOffset) // Arrow tip
            lineTo(centerX - 20, centerY - radius - 50 + arrowOffset) // Left corner
            lineTo(centerX + 20, centerY - radius - 50 + arrowOffset) // Right corner
            close()
        },
        color = Color.Black
    )
}