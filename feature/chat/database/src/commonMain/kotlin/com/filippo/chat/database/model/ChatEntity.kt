package com.filippo.chat.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatEntity(
    @PrimaryKey val id: String,
    val lastActivityTimestamp: Long,
)
