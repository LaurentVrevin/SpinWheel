package com.laurentvrevin.spinwheel.utils

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import kotlin.random.Random

suspend fun spinWheel(
    rotation: Animatable<Float, AnimationVector1D>,
    anglePerItem: Float,
    items: List<String>,
    impulseDuration: Long,
    onSpinCompleted: (String) -> Unit,
    hapticFeedback: HapticFeedback? = null

) {
    // Calcul du facteur d'impulsion pour ajuster la durée et l'amplitude de la rotation
    val impulseFactor = (impulseDuration / 100f).coerceIn(1f, 10f)
    val targetRotation = (impulseFactor * 360) + Random.nextInt(0, 360)

    // Animer la rotation
    rotation.animateTo(
        targetValue = rotation.value + targetRotation,
        animationSpec = tween(
            durationMillis = (2000 + (impulseFactor * 100)).toInt(),
            easing = FastOutSlowInEasing
        ),
        block = {
            if (value.toInt() % 30 == 0) {
                hapticFeedback?.performHapticFeedback(HapticFeedbackType.LongPress)
            }
        }
    )

    // Calcul de l'angle final (normalisé entre 0 et 360)
    val finalRotation = (rotation.value % 360f + 360f) % 360f

    // Ajuster l'angle pour correspondre à l'élément sélectionné
    val adjustedRotation = (360f - finalRotation + 270f) % 360f
    val selectedIndex = ((adjustedRotation / anglePerItem).toInt()) % items.size

    // Appeler le callback avec l'élément sélectionné
    onSpinCompleted(items[selectedIndex])
}

