package com.filippo.auth.presentation.register.success

import com.filippo.core.presentation.util.UiText

internal data class RegisterSuccessState(
    val registeredEmail: String = "",
    val isResendingEmail: Boolean = false,
    val resendEmailError: UiText? = null,
)
