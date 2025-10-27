package com.filippo.chat.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class MessageWithSenderEntity(
    @Embedded val message: ChatMessageEntity,
    @Relation(
        parentColumn = "senderId",
        entityColumn = "id"
    )
    val sender: ChatParticipantEntity
)
