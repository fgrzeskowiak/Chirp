package com.filippo.chat.domain.models

data class ChatMessageWithParticipant(
    val message: ChatMessage,
    val sender: ChatParticipant,
)
