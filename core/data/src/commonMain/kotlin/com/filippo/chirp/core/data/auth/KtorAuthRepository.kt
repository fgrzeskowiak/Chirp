package com.filippo.chirp.core.data.auth

import com.filippo.chirp.core.data.mapper.toDomain
import com.filippo.chirp.core.data.model.EmailRequest
import com.filippo.chirp.core.data.model.LoginRequest
import com.filippo.chirp.core.data.model.LoginResponse
import com.filippo.chirp.core.data.model.RegisterRequest
import com.filippo.chirp.core.data.networking.get
import com.filippo.chirp.core.data.networking.post
import com.filippo.core.domain.RemoteResult
import com.filippo.core.domain.auth.AuthRepository
import com.filippo.core.domain.auth.Session
import io.ktor.client.HttpClient
import jakarta.inject.Singleton

@Singleton
internal class KtorAuthRepository(
    private val httpClient: HttpClient
) : AuthRepository {

    override suspend fun login(email: String, password: String): RemoteResult<Session> =
        httpClient.post<LoginRequest, LoginResponse>(
            route = "/api/auth/login",
            body = LoginRequest(email, password),
        ).map { it.toDomain() }

    override suspend fun register(
        email: String,
        password: String,
        username: String,
    ): RemoteResult<Unit> =
        httpClient.post(
            route = "/api/auth/register",
            body = RegisterRequest(email = email, username = username, password = password)
        )

    override suspend fun resendVerificationEmail(email: String): RemoteResult<Unit> =
        httpClient.post(
            route = "/api/auth/resend-verification",
            body = EmailRequest(email),
        )

    override suspend fun verifyEmail(token: String): RemoteResult<Unit> =
        httpClient.get(
            route = "/api/auth/verify",
            params = mapOf("token" to token)
        )

    override suspend fun forgotPassword(email: String): RemoteResult<Unit> =
        httpClient.post(
            route = "/api/auth/forgot-password",
            body = EmailRequest(email),
        )
}
