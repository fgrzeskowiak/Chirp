package com.filippo.chat.data.mappers

import com.filippo.chat.data.model.ChatParticipantResponse
import com.filippo.chat.data.model.ChatResponse
import com.filippo.chat.domain.models.Chat
import com.filippo.chat.domain.models.ChatMessage
import com.filippo.chat.domain.models.ChatParticipant
import kotlin.time.Instant

fun ChatParticipantResponse.toDomain() = ChatParticipant(
    userId = userId,
    username = username,
    profilePictureUrl = profilePictureUrl,
)

fun ChatResponse.toDomain() = Chat(
    id = id,
    participants = participants.map { it.toDomain() },
    lastActivityAt = Instant.parse(lastActivityAt),
    lastMessage = lastMessage?.toDomain(),
)

fun ChatResponse.Message.toDomain() = ChatMessage(
    id = id,
    chatId = chatId,
    content = content,
    createdAt = Instant.parse(createdAt),
    senderId = senderId,
)
