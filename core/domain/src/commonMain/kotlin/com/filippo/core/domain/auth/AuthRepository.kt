package com.filippo.core.domain.auth

import com.filippo.core.domain.RemoteResult

interface AuthRepository {
    suspend fun login(email: String, password: String): RemoteResult<Session>
    suspend fun register(email: String, password: String, username: String): RemoteResult<Unit>
    suspend fun resendVerificationEmail(email: String): RemoteResult<Unit>
    suspend fun verifyEmail(token: String): RemoteResult<Unit>
    suspend fun forgotPassword(email: String): RemoteResult<Unit>
}
