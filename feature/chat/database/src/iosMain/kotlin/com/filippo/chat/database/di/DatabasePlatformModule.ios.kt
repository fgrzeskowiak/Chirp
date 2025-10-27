package com.filippo.chat.database.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.filippo.chat.database.ChirpDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@Module
@Configuration
@OptIn(ExperimentalForeignApi::class)
actual class DatabasePlatformModule {

    @Factory
    fun databaseFactory(): RoomDatabase.Builder<ChirpDatabase> {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )

        val dbFile = buildString {
            append(requireNotNull(documentDirectory?.path))
            append("/")
            append(ChirpDatabase.DB_NAME)
        }

        return Room.databaseBuilder(dbFile)
    }
}