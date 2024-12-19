package com.laurentvrevin.spinwheel.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke

// Draw the wheel's border
fun DrawScope.drawWheelBorder(
    centerX: Float,
    centerY: Float,
    radius: Float
) {
    drawCircle(
        color = Color.Black,
        radius = radius + 5f,
        center = Offset(centerX, centerY),
        style = Stroke(10f)
    )
}