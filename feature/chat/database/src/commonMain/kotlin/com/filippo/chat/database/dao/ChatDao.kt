package com.filippo.chat.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.filippo.chat.database.model.ChatDetailsEntity
import com.filippo.chat.database.model.ChatEntity
import com.filippo.chat.database.model.ChatWithParticipants
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {

    @Upsert
    suspend fun upsert(chat: ChatEntity)

    @Upsert
    suspend fun upsert(chats: List<ChatEntity>)

    @Transaction
    @Query("""
       SELECT DISTINCT chat.* 
        FROM chatentity chat 
        JOIN chatparticipantcrossref chatCrossRef ON chat.id = chatCrossRef.chatId
        ORDER BY lastActivityTimestamp DESC
    """
    )
    fun getChatsWithParticipants(): Flow<List<ChatWithParticipants>>

    @Transaction
    @Query("SELECT * FROM chatentity WHERE id = :id")
    suspend fun getChatById(id: String): ChatWithParticipants?

    @Query("SELECT id FROM chatentity")
    suspend fun getAllChatIds(): List<String>

    @Query("DELETE FROM chatentity WHERE id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM chatentity")
    suspend fun deleteAll()

    @Transaction
    suspend fun delete(ids: List<String>) {
        ids.forEach { delete(it) }
    }

    @Query("SELECT COUNT(*) FROM chatentity")
    fun getChatCount(): Flow<Int>

    @Transaction
    @Query("SELECT * FROM chatentity WHERE id = :id")
    fun getChatInfo(id: String): Flow<ChatDetailsEntity?>
}