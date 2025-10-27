package com.filippo.chat.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ChatWithParticipants(
    @Embedded
    val chat: ChatEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            ChatParticipantCrossRef::class,
            parentColumn = "chatId",
            entityColumn = "userId"
        )
    )
    val participants: List<ChatParticipantEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "chatId",
        entity = LastMessageView::class
    )
    val lastMessage: LastMessageView?,
)

