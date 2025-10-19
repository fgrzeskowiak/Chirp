package com.filippo.core.designsystem.components.textfields

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.filippo.core.designsystem.theme.ChirpTheme
import com.filippo.core.designsystem.theme.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    title: String? = null,
    supportingText: String? = null,
    hasError: Boolean = false,
    singleLine: Boolean = false,
    isEnabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    onFocusChanged: (Boolean) -> Unit = {},
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val isFocused by interactionSource.collectIsFocusedAsState()

    LaunchedEffect(isFocused) {
        onFocusChanged(isFocused)
    }

    ChirpTextFieldLayout(
        modifier = modifier,
        title = title,
        supportingText = supportingText,
        hasError = hasError,
    ) {
        BasicTextField(
            modifier = Modifier.fillMaxWidth()
                .background(
                    color = when {
                        isFocused -> MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
                        isEnabled -> MaterialTheme.colorScheme.surface
                        else -> MaterialTheme.colorScheme.extended.secondaryFill
                    },
                    shape = RoundedCornerShape(8.dp),
                )
                .border(
                    width = 1.dp,
                    color = when {
                        hasError -> MaterialTheme.colorScheme.error
                        isFocused -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.outline
                    },
                    shape = RoundedCornerShape(8.dp),
                )
                .padding(12.dp),
            state = state,
            enabled = isEnabled,
            lineLimits = if (singleLine) TextFieldLineLimits.SingleLine else TextFieldLineLimits.Default,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = if (isEnabled) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.extended.textPlaceholder
                }
            ),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            onKeyboardAction = onKeyboardAction,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
            interactionSource = interactionSource,
            decorator = { innerBox ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (state.text.isEmpty() && placeholder != null) {
                        Text(
                            text = placeholder,
                            color = MaterialTheme.colorScheme.extended.textPlaceholder,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                    innerBox()
                }
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ChirpTextFieldEmptyPreview() {
    ChirpTheme {
        ChirpTextField(
            state = rememberTextFieldState(),
            modifier = Modifier.width(300.dp),
            placeholder = "test@test.com",
            title = "Email",
            supportingText = "Please enter your email",
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ChirpTextFieldFilledPreview() {
    ChirpTheme {
        ChirpTextField(
            state = rememberTextFieldState(initialText = "test@test.com"),
            modifier = Modifier.width(300.dp),
            placeholder = "test@test.com",
            title = "Email",
            supportingText = "Please enter your email",
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ChirpTextFieldDisabledFilledPreview() {
    ChirpTheme {
        ChirpTextField(
            state = rememberTextFieldState(initialText = "test@test.com"),
            modifier = Modifier.width(300.dp),
            placeholder = "test@test.com",
            title = "Email",
            supportingText = "Please enter your email",
            isEnabled = false,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ChirpTextFieldFilledErrorPreview() {
    ChirpTheme {
        ChirpTextField(
            state = rememberTextFieldState(initialText = "test@test.com"),
            modifier = Modifier.width(300.dp),
            placeholder = "test@test.com",
            title = "Email",
            supportingText = "This is not a valid email",
            isEnabled = true,
            hasError = true,
        )
    }
}
