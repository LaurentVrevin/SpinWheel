package com.laurentvrevin.spinwheel.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

// Draw an individual segment of the wheel
fun DrawScope.drawSegment(
    startAngle: Float,
    sweepAngle: Float,
    radius: Float,
    centerX: Float,
    centerY: Float,
    color: Color
) {
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = true,
        size = Size(radius * 2, radius * 2),
        topLeft = Offset(centerX - radius, centerY - radius)
    )
}