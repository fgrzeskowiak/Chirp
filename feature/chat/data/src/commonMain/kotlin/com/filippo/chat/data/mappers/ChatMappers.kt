package com.filippo.chat.data.mappers

import com.filippo.chat.data.model.ChatResponse
import com.filippo.chat.database.model.ChatDetailsEntity
import com.filippo.chat.database.model.ChatEntity
import com.filippo.chat.database.model.ChatWithParticipants
import com.filippo.chat.domain.models.Chat
import com.filippo.chat.domain.models.ChatDetails
import com.filippo.chat.domain.models.ChatMessage
import com.filippo.chat.domain.models.ChatParticipant
import com.filippo.chat.domain.models.MessageDeliveryStatus
import kotlin.time.Instant

fun ChatResponse.toDomain() = Chat(
    id = id,
    participants = participants.map { it.toDomain() },
    lastActivityTimestamp = Instant.parse(lastActivityAt),
    lastMessage = lastMessage?.toDomain(),
)

private fun ChatResponse.Message.toDomain() = ChatMessage(
    id = id,
    chatId = chatId,
    content = content,
    createdAt = Instant.parse(createdAt),
    senderId = senderId,
    deliveryStatus = MessageDeliveryStatus.SENT,
)

fun ChatWithParticipants.toDomain() = Chat(
    id = chat.id,
    participants = participants.map { it.toDomain() },
    lastMessage = lastMessage?.toDomain(),
    lastActivityTimestamp = Instant.fromEpochMilliseconds(chat.lastActivityTimestamp)
)

fun Chat.toEntity() = ChatEntity(
    id = id,
    lastActivityTimestamp = lastActivityTimestamp.toEpochMilliseconds()
)

fun ChatEntity.toDomain(
    participants: List<ChatParticipant>,
    lastMessage: ChatMessage?,
) = Chat(
    id = id,
    participants = participants,
    lastActivityTimestamp = Instant.fromEpochMilliseconds(lastActivityTimestamp),
    lastMessage = lastMessage,
)

fun ChatDetailsEntity.toDomain() = ChatDetails(
    chat = chat.toDomain(
        participants = participants.map { it.toDomain() },
        lastMessage = null
    ),
    messages = messagesWithSenders.map { it.toDomain() }
)