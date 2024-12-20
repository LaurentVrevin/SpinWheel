package com.laurentvrevin.spinwheel.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.sin


fun DrawScope.drawSpinningWheel(
    items: List<String>,
    anglePerItem: Float,
    rotationValue: Float
) {
    val radius = size.minDimension / 2
    val centerX = size.width / 2
    val centerY = size.height / 2

    // Draw the wheel's border
    drawWheelBorder(centerX, centerY, radius)

    // Draw each segment and its text
    items.forEachIndexed { index, item ->
        val startAngle = index * anglePerItem + rotationValue
        val middleAngle = startAngle + anglePerItem / 2

        // Forcing alternation by checking whether the segment is in the first half of the wheel
        val segmentColor = if (index % 2 == 0) Color.White else Color.Black
        //Voir pour ajuster les items afin d'en avoir toujours en Pair !
        //val segmentColorif (items.size % 2 == 1) items + " " else items

        drawSegment(
            startAngle = startAngle,
            sweepAngle = anglePerItem,
            radius = radius,
            centerX = centerX,
            centerY = centerY,
            color = segmentColor
        )

        drawSegmentText(
            item = item,
            middleAngle = middleAngle,
            textRadius = radius * 0.6f,
            centerX = centerX,
            centerY = centerY,
            textColor = if (segmentColor == Color.Black) android.graphics.Color.WHITE else android.graphics.Color.BLACK
        )
    }

    // Calculate and draw the arrow
    val arrowOffset = 10 * sin(Math.toRadians(rotationValue.toDouble())).toFloat()
    drawArrow(centerX, centerY, radius, arrowOffset)
}
