package com.filippo.chat.domain.models

import kotlin.time.Instant

data class Chat(
    val id: String,
    val participants: List<ChatParticipant>,
    val lastActivityTimestamp: Instant,
    val lastMessage: ChatMessage?,
)