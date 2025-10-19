package com.filippo.auth.presentation.email_verification

internal data class EmailVerificationState(
    val isVerifying: Boolean = false,
    val isVerified: Boolean = false,
)
