package com.filippo.core.domain.auth

data class Session(
    val accessToken: String,
    val refreshToken: String,
    val user: User,
)
