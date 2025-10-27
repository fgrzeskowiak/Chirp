package com.filippo.core.presentation.util

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.DividerDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager

@Composable
fun Modifier.clearFocusOnTap(): Modifier = composed {
    val focusManager = LocalFocusManager.current
    pointerInput(Unit) {
        detectTapGestures {
            focusManager.clearFocus()
        }
    }
}

fun Modifier.drawDivider(color: Color) = drawWithContent {
    drawContent()
    val strokeWidth = DividerDefaults.Thickness.toPx()
    drawLine(
        color = color,
        start = Offset(0f, size.height - strokeWidth / 2),
        end = Offset(size.width, size.height - strokeWidth / 2),
        strokeWidth = strokeWidth,
    )
}