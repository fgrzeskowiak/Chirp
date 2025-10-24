package com.filippo.chat.domain.models

data class ChatDetails(
    val chat: Chat,
    val messages: List<MessageWithSender>
)