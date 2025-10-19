package com.filippo.auth.domain.validation

data class PasswordValidationState(
    val hasMinLength: Boolean = false,
    val hasDigit: Boolean = false,
    val hasUpperCase: Boolean = false,
) {
    val isValid = hasMinLength && hasDigit && hasUpperCase
}
