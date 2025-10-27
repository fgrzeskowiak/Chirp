package com.filippo.chat.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.filippo.chat.database.model.ChatMessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatMessageDao {
    @Upsert
    suspend fun upsert(message: ChatMessageEntity)

    @Upsert
    suspend fun upsert(messages: List<ChatMessageEntity>)

    @Query("DELETE FROM chatmessageentity WHERE id = :id")
    suspend fun deleteMessage(id: String)

    @Transaction
    suspend fun deleteMessages(ids: List<String>) {
        ids.forEach { deleteMessage(it) }
    }

    @Query("SELECT * FROM chatmessageentity WHERE chatId = :chatId ORDER BY timestamp DESC")
    fun getChatMessages(chatId: String): Flow<List<ChatMessageEntity>>

    @Query("SELECT * FROM chatmessageentity WHERE id = :id")
    suspend fun getMessage(id: String): ChatMessageEntity?
}