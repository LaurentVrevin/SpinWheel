package com.laurentvrevin.spinwheel.utils

import android.graphics.Typeface
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import kotlin.math.cos
import kotlin.math.sin

// Draw text within a segment
fun DrawScope.drawSegmentText(
    item: String,
    middleAngle: Float,
    textRadius: Float,
    centerX: Float,
    centerY: Float,
    textColor: Int
) {
    val textX = centerX + textRadius * cos(Math.toRadians(middleAngle.toDouble())).toFloat()
    val textY = centerY + textRadius * sin(Math.toRadians(middleAngle.toDouble())).toFloat()

    withTransform({
        rotate(middleAngle, Offset(textX, textY))
    }) {
        drawContext.canvas.nativeCanvas.drawText(
            item,
            textX,
            textY,
            android.graphics.Paint().apply {
                textAlign = android.graphics.Paint.Align.CENTER
                color = textColor
                textSize = 36f
                isAntiAlias = true
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }
        )
    }
}