package com.filippo.core.designsystem.components.textfields

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.eye_icon
import chirp.core.designsystem.generated.resources.eye_off_icon
import chirp.core.designsystem.generated.resources.hide_password
import chirp.core.designsystem.generated.resources.show_password
import com.filippo.core.designsystem.theme.ChirpTheme
import com.filippo.core.designsystem.theme.extended
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpPasswordTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    title: String? = null,
    supportingText: String? = null,
    hasError: Boolean = false,
    isEnabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    onKeyboardAction: KeyboardActionHandler? = null,
    onFocusChanged: (Boolean) -> Unit = {},
) {
    ChirpTextFieldLayout(
        modifier = modifier,
        title = title,
        supportingText = supportingText,
        hasError = hasError,
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val isFocused by interactionSource.collectIsFocusedAsState()
        var isPasswordVisible by remember { mutableStateOf(false) }

        LaunchedEffect(isFocused) {
            onFocusChanged(isFocused)
        }

        BasicSecureTextField(
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
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = if (isEnabled) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.extended.textPlaceholder
                }
            ),
            textObfuscationMode = if (isPasswordVisible) {
                TextObfuscationMode.Visible
            } else {
                TextObfuscationMode.RevealLastTyped
            },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            onKeyboardAction = onKeyboardAction,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
            interactionSource = interactionSource,
            decorator = { innerBox ->
                TextFieldContent(
                    placeholder = placeholder?.takeIf { state.text.isEmpty() },
                    isPasswordVisible = isPasswordVisible,
                    onToggleVisibilityClick = { isPasswordVisible = !isPasswordVisible },
                    innerBox = innerBox,
                )
            }
        )
    }
}

@Composable
private fun TextFieldContent(
    placeholder: String?,
    isPasswordVisible: Boolean,
    onToggleVisibilityClick: () -> Unit,
    innerBox: @Composable (() -> Unit)
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart
        ) {
            if (placeholder != null) {
                Text(
                    text = placeholder,
                    color = MaterialTheme.colorScheme.extended.textPlaceholder,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            innerBox()
        }

        ToggleVisibilityIcon(
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(
                        bounded = false,
                        radius = 24.dp
                    ),
                    onClick = onToggleVisibilityClick
                ),
            isPasswordVisible = isPasswordVisible,
        )
    }
}

@Composable
private fun ToggleVisibilityIcon(
    isPasswordVisible: Boolean,
    modifier: Modifier = Modifier,
) {
    Icon(
        modifier = modifier,
        imageVector = vectorResource(
            if (isPasswordVisible) {
                Res.drawable.eye_off_icon
            } else {
                Res.drawable.eye_icon
            }
        ),
        contentDescription = stringResource(
            if (isPasswordVisible) {
                Res.string.hide_password
            } else {
                Res.string.show_password
            }
        ),
        tint = MaterialTheme.colorScheme.extended.textDisabled,
    )
}


@Composable
@Preview(showBackground = true)
private fun ChirpPasswordTextFieldEmptyPreview() {
    ChirpTheme {
        ChirpPasswordTextField(
            state = rememberTextFieldState(),
            modifier = Modifier.width(300.dp),
            placeholder = "Password",
            title = "Password",
            supportingText = "Use 9+ characters, at least one digit and one uppercase letter",
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ChirpPasswordTextFieldFilledPreview() {
    ChirpTheme {
        ChirpPasswordTextField(
            state = rememberTextFieldState("password123"),
            modifier = Modifier.width(300.dp),
            placeholder = "Password",
            title = "Password",
            supportingText = "Use 9+ characters, at least one digit and one uppercase letter",
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ChirpPasswordTextFieldErrorPreview() {
    ChirpTheme {
        ChirpPasswordTextField(
            state = rememberTextFieldState("password123"),
            modifier = Modifier
                .width(300.dp),
            placeholder = "Password",
            title = "Password",
            supportingText = "Doesn't contain an uppercase character",
            hasError = true,
        )
    }
}
