package com.filippo.chat.database.model

import androidx.room.DatabaseView

@DatabaseView(
    viewName = "last_message_view_per_chat",
    value = """
        SELECT message.*
        FROM chatmessageentity message
        JOIN (
            SELECT chatId, MAX(timestamp) AS max_timestamp
            FROM chatmessageentity
            GROUP BY chatId
        ) latestTimestampByChatId 
        ON message.chatId == latestTimestampByChatId.chatId 
        AND message.timestamp = max_timestamp
    """
)
data class LastMessageView(
    val id: String,
    val chatId: String,
    val senderId: String,
    val content: String,
    val timestamp: Long,
    val deliveryStatus: String,
)