package com.filippo.chirp.core.data.mapper

import com.filippo.chirp.core.data.model.LoginResponse
import com.filippo.core.domain.auth.Session
import com.filippo.core.domain.auth.User

fun LoginResponse.toDomain() = Session(
    accessToken = accessToken,
    refreshToken = refreshToken,
    user = user.toDomain(),
)

fun LoginResponse.User.toDomain() = User(
    id = id,
    email = email,
    username = username,
    hasVerifiedEmail = isEmailVerified,
    profilePictureUrl = profilePictureUrl
)

fun Session.toResponse() = LoginResponse(
    accessToken = accessToken,
    refreshToken = refreshToken,
    user = user.toResponse()
)

private fun User.toResponse(): LoginResponse.User = LoginResponse.User(
    id = id,
    email = email,
    username = username,
    isEmailVerified = hasVerifiedEmail,
    profilePictureUrl = profilePictureUrl
)
