package com.filippo.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatResponse(
    val id: String,
    val participants: List<ChatParticipantResponse>,
    val lastActivityAt: String,
    val lastMessage: Message?
) {
    @Serializable
    data class Message(
        val id: String,
        val chatId: String,
        val content: String,
        val createdAt: String,
        val senderId: String
    )
}