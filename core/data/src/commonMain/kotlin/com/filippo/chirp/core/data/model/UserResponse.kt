package com.filippo.chirp.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: User,
) {
    @Serializable
    data class User(
        val id: String,
        val email: String,
        val username: String,
        val isEmailVerified: Boolean,
        val profilePictureUrl: String? = null,
    )
}
