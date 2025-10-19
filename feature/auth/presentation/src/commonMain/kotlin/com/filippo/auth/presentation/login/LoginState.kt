package com.filippo.auth.presentation.login

import androidx.compose.foundation.text.input.TextFieldState
import com.filippo.core.presentation.util.UiText

data class LoginState(
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val canLogin: Boolean = true,
    val isLoggingIn: Boolean = false,
    val error: UiText? = null,
)
