package com.filippo.chat.database

import androidx.room.RoomDatabaseConstructor

@Suppress("KotlinNoActualForExpect")
expect object DatabaseConstructor: RoomDatabaseConstructor<ChirpDatabase> {
    override fun initialize(): ChirpDatabase
}