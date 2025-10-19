package com.filippo.auth.domain.forgot_password

import arrow.core.raise.either
import com.filippo.auth.domain.AuthError
import com.filippo.auth.domain.validation.EmailValidator
import com.filippo.core.domain.auth.AuthRepository

class ForgotPasswordUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String) = either {
        EmailValidator.validate(email).mapLeft { AuthError.Validation(listOf(it)) }.bind()
        authRepository.forgotPassword(email).mapLeft(AuthError::Request).bind()
    }
}
