package com.filippo.chat.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    primaryKeys = ["chatId", "userId"],
    foreignKeys = [
        ForeignKey(
            entity = ChatEntity::class,
            parentColumns = ["id"],
            childColumns = ["chatId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = ChatParticipantEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [
        Index("chatId"),
        Index("userId"),
    ]
)
data class ChatParticipantCrossRef(
    val chatId: String,
    val userId: String,
)
