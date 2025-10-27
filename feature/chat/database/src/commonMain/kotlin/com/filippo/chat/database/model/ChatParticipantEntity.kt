package com.filippo.chat.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatParticipantEntity(
    @PrimaryKey val id: String,
    val username: String,
    val profilePictureUrl: String?,
)
