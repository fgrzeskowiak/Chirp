package com.filippo.chat.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.filippo.chat.database.model.ChatParticipantCrossRef

@Dao
interface ChatParticipantsCrossRefDao {

    @Upsert
    suspend fun upsert(crossRefs: List<ChatParticipantCrossRef>)

//    @Query("SELECT userId FROM chatparticipantcrossref WHERE chatId = :chatId AND isActive = 1")
//    suspend fun getActiveChatParticipantIds(chatId: String): List<String>

    @Query("SELECT userId FROM chatparticipantcrossref WHERE chatId = :chatId")
    suspend fun getAllChatParticipantIds(chatId: String): List<String>

    @Query("SELECT * FROM chatparticipantcrossref")
    suspend fun getAll(): List<ChatParticipantCrossRef>

//    @Query("SELECT isActive FROM chatparticipantcrossref WHERE userId = :userId")
//    suspend fun getIsActive(userId: String): Boolean
//
//    @Query("UPDATE chatparticipantcrossref SET isActive = :isActive WHERE userId = :userId")
//    suspend fun setIsActive(userId: String, isActive: Boolean)

//    @Query(
//        """
//        UPDATE chatparticipantcrossref
//        SET isActive = 0
//        WHERE chatId = :chatId AND userId IN (:userIds)
//    """
//    )
//    suspend fun decativate(chatId: String, userIds: List<String>)
//
//    @Query(
//        """
//        UPDATE chatparticipantcrossref
//        SET isActive = 1
//        WHERE chatId = :chatId AND userId IN (:userIds)
//    """
//    )
//    suspend fun reactivate(chatId: String, userIds: List<String>)

    @Delete
    suspend fun delete(crossRefs: List<ChatParticipantCrossRef>)

    @Query("DELETE FROM chatparticipantcrossref WHERE userId IN(:userIds)")
    suspend fun deleteByUserId(userIds: List<String>)
}