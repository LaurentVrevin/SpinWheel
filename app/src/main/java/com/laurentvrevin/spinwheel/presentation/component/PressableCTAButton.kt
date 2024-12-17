package com.laurentvrevin.spinwheel.presentation.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun PressableCTAButton(
    text: String,
    onSpinRequested: (Long) -> Unit, // Handle spin with press duration
    modifier: Modifier = Modifier
) {
    // States for button press and duration tracking
    var pressStartTime by remember { mutableLongStateOf(0L) }
    var isPressed by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Button scale animation for press effect
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = androidx.compose.animation.core.spring(), label = ""
    )

    // Button surface with interaction logic
    Surface(
        modifier = modifier
            .scale(scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        pressStartTime = System.currentTimeMillis()
                        tryAwaitRelease()
                        isPressed = false
                        val pressDuration = System.currentTimeMillis() - pressStartTime
                        coroutineScope.launch {
                            onSpinRequested(pressDuration)
                        }
                    }
                )
            },
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFFB8C00),
        shadowElevation = 8.dp
    ) {
        // Centered text inside the button
        Box(
            modifier = Modifier
                .padding(horizontal = 48.dp, vertical = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                fontWeight = FontWeight.Bold
            )
        }
    }
}