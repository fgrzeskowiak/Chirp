package com.filippo.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatParticipantResponse(
    val userId: String,
    val username: String,
    val profilePictureUrl: String?
)
