package com.filippo.auth.presentation.forgot_password

import androidx.compose.foundation.text.input.TextFieldState
import com.filippo.core.presentation.util.UiText

internal data class ForgotPasswordState(
    val email: TextFieldState = TextFieldState(),
    val isLoading: Boolean = false,
    val validationError: UiText? = null,
    val requestError: UiText? = null,
    val isSuccess: Boolean = false,
    val canSubmit: Boolean = true,
)
