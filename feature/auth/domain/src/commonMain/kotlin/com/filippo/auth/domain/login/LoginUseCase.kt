package com.filippo.auth.domain.login

import arrow.core.Either
import arrow.core.raise.either
import com.filippo.core.domain.DataError
import com.filippo.core.domain.auth.AuthRepository
import com.filippo.core.domain.auth.SessionStorage
import jakarta.inject.Inject


class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionStorage: SessionStorage,
) {

    suspend operator fun invoke(email: String, password: String): Either<DataError.Remote, Unit> =
        either {
            val response = authRepository.login(email, password).bind()
            sessionStorage.set(response)
        }
}
