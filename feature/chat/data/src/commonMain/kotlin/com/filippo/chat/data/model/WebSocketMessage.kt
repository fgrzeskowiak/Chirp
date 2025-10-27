package com.filippo.chat.data.model

data class WebSocketMessage(
    val type: String,
    val content: String
)