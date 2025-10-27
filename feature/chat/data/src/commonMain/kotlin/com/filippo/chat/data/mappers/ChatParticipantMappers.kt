package com.filippo.chat.data.mappers

import com.filippo.chat.data.model.ChatParticipantResponse
import com.filippo.chat.database.model.ChatParticipantEntity
import com.filippo.chat.domain.models.ChatParticipant

fun ChatParticipantResponse.toDomain() = ChatParticipant(
    userId = userId,
    username = username,
    profilePictureUrl = profilePictureUrl,
)

fun ChatParticipantEntity.toDomain() = ChatParticipant(
    userId = id,
    username = username,
    profilePictureUrl = profilePictureUrl,
)

fun ChatParticipant.toEntity() = ChatParticipantEntity(
    id = userId,
    username = username,
    profilePictureUrl = profilePictureUrl
)