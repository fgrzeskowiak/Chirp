package com.filippo.chat.database.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.filippo.chat.database.ChirpDatabase
import jakarta.inject.Singleton
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module

@Module(includes = [DatabasePlatformModule::class])
@Configuration
class DatabaseModule {

    @Singleton
    fun database(builder: RoomDatabase.Builder<ChirpDatabase>) =
        builder.setDriver(BundledSQLiteDriver()).build()
}