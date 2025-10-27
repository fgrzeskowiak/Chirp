package com.filippo.chat.data.mappers

import com.filippo.chat.database.model.ChatMessageEntity
import com.filippo.chat.database.model.LastMessageView
import com.filippo.chat.database.model.MessageWithSenderEntity
import com.filippo.chat.domain.models.ChatMessage
import com.filippo.chat.domain.models.ChatMessageWithParticipant
import com.filippo.chat.domain.models.MessageDeliveryStatus
import kotlin.time.Instant

fun LastMessageView.toDomain() = ChatMessage(
    id = id,
    chatId = chatId,
    content = content,
    createdAt = Instant.fromEpochMilliseconds(timestamp),
    senderId = senderId,
    deliveryStatus = MessageDeliveryStatus.valueOf(deliveryStatus),
)

fun ChatMessage.toEntity() = ChatMessageEntity(
    id = id,
    chatId = chatId,
    senderId = senderId,
    content = content,
    timestamp = createdAt.toEpochMilliseconds(),
    deliveryStatus = deliveryStatus.name
)

fun ChatMessageEntity.toDomain() = ChatMessage(
    id = id,
    chatId = chatId,
    content = content,
    createdAt = Instant.fromEpochMilliseconds(timestamp),
    senderId = senderId,
    deliveryStatus = MessageDeliveryStatus.valueOf(deliveryStatus),
)

fun MessageWithSenderEntity.toDomain() = ChatMessageWithParticipant(
    message = message.toDomain(),
    sender = sender.toDomain()
)