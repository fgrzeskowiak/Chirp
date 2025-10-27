package com.filippo.chat.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.filippo.chat.database.ChirpDatabase
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
@Configuration
actual class DatabasePlatformModule {

    @Factory
    fun databaseFactory(context: Context): RoomDatabase.Builder<ChirpDatabase> =
        Room.databaseBuilder(
            context = context,
            name = context.getDatabasePath(ChirpDatabase.DB_NAME).absolutePath
        )
}