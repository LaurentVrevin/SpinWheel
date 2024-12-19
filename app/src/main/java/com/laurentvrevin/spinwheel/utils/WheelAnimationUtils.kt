package com.laurentvrevin.spinwheel.utils

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import kotlin.random.Random

suspend fun animateWheelSpin(
    rotation: Animatable<Float, AnimationVector1D>,
    anglePerItem: Float,
    items: List<String>,
    impulseDuration: Long,
    onSpinCompleted: (String) -> Unit,
    hapticFeedback: HapticFeedback? = null
) {
    // Calculate the impulse factor to adjust spin duration and amplitude
    val impulseFactor = (impulseDuration / 100f).coerceIn(1f, 10f)
    val targetRotation = (impulseFactor * 360) + Random.nextInt(0, 360)

    // Animate the rotation
    rotation.animateTo(
        targetValue = rotation.value + targetRotation,
        animationSpec = tween(
            durationMillis = (2000 + (impulseFactor * 100)).toInt(),
            easing = FastOutSlowInEasing
        ),
        block = {
            // Trigger haptic feedback periodically during rotation
            if (value.toInt() % 30 == 0) {
                hapticFeedback?.performHapticFeedback(HapticFeedbackType.LongPress)
            }
        }
    )

    // Normalize the final rotation angle between 0 and 360 degrees
    val finalRotation = (rotation.value % 360f + 360f) % 360f

    // Adjust the angle to find the selected item
    val adjustedRotation = (360f - finalRotation + 270f) % 360f
    val selectedIndex = ((adjustedRotation / anglePerItem).toInt()) % items.size

    // Invoke the callback with the selected item
    onSpinCompleted(items[selectedIndex])
}