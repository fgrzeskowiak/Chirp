package com.filippo.chat.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.filippo.chat.database.dao.ChatDao
import com.filippo.chat.database.dao.ChatMessageDao
import com.filippo.chat.database.dao.ChatParticipantDao
import com.filippo.chat.database.dao.ChatParticipantsCrossRefDao
import com.filippo.chat.database.model.ChatEntity
import com.filippo.chat.database.model.ChatMessageEntity
import com.filippo.chat.database.model.ChatParticipantCrossRef
import com.filippo.chat.database.model.ChatParticipantEntity
import com.filippo.chat.database.model.LastMessageView

@Database(
    entities = [
        ChatEntity::class,
        ChatParticipantEntity::class,
        ChatMessageEntity::class,
        ChatParticipantCrossRef::class,
    ],
    views = [LastMessageView::class],
    version = 1,
)
@ConstructedBy(DatabaseConstructor::class)
abstract class ChirpDatabase : RoomDatabase() {
    abstract val chatDao: ChatDao
    abstract val chatParticipantDao: ChatParticipantDao
    abstract val chatMessageDao: ChatMessageDao
    abstract val crossRefDao: ChatParticipantsCrossRefDao

    companion object {
        const val DB_NAME = "chirp.db"
    }
}