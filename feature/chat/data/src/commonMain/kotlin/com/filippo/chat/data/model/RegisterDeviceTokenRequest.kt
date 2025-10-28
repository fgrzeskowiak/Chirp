package com.filippo.chat.data.model

data class RegisterDeviceTokenRequest(
    val token: String,
    val platform: String,
)
