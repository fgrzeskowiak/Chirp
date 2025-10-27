package com.filippo.chat.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.filippo.chat.database.model.ChatParticipantEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatParticipantDao {
    @Upsert
    suspend fun upsert(participant: ChatParticipantEntity)

    @Upsert
    suspend fun upsert(participants: List<ChatParticipantEntity>)

    @Query("SELECT * FROM chatparticipantentity")
    suspend fun getAllParticipants(): List<ChatParticipantEntity>

    @Query("SELECT * FROM chatparticipantentity WHERE id = :id")
    suspend fun getParticipant(id: String): ChatParticipantEntity?

    @Query("SELECT * FROM chatparticipantentity WHERE username LIKE '%' || :username || '%'")
    suspend fun getParticipantsByUsername(username: String): List<ChatParticipantEntity>

    @Query("""
       SELECT participant.*
        FROM ChatParticipantEntity participant
        JOIN chatparticipantcrossref crossRef ON participant.id = crossRef.userId
        WHERE crossRef.chatId = :chatId
        ORDER BY participant.username
    """)
    fun getChatParticipants(chatId: String): Flow<List<ChatParticipantEntity>>
}