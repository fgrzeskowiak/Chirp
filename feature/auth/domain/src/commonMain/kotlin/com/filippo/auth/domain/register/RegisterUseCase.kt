package com.filippo.auth.domain.register

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.zipOrAccumulate
import com.filippo.auth.domain.AuthError
import com.filippo.auth.domain.validation.EmailValidator
import com.filippo.auth.domain.validation.PasswordValidator
import com.filippo.auth.domain.validation.UsernameValidator
import com.filippo.auth.domain.validation.ValidationError
import com.filippo.core.domain.auth.AuthRepository
import jakarta.inject.Inject

class RegisterUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(
        email: String,
        password: String,
        username: String,
    ): Either<AuthError, Unit> = either {
        validateInputs(email, password, username).mapLeft(AuthError::Validation).bind()
        authRepository.register(email, password, username).mapLeft(AuthError::Request).bind()
    }

    private fun validateInputs(
        email: String,
        password: String,
        username: String,
    ): Either<List<ValidationError>, Unit> = either {
        zipOrAccumulate(
            { EmailValidator.validate(email).bind() },
            { PasswordValidator.validate(password).bind() },
            { UsernameValidator.validate(username).bind() },
            { _, _, _ -> }
        )
    }
}
