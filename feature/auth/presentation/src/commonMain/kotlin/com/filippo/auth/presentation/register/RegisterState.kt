package com.filippo.auth.presentation.register

import androidx.compose.foundation.text.input.TextFieldState
import com.filippo.core.presentation.util.UiText

data class RegisterState(
    val email: TextFieldState = TextFieldState(),
    val emailError: UiText? = null,
    val password: TextFieldState = TextFieldState(),
    val passwordError: UiText? = null,
    val username: TextFieldState = TextFieldState(),
    val usernameError: UiText? = null,
    val registrationError: UiText? = null,
    val isRegistering: Boolean = false,
    val canRegister: Boolean = true,
)
