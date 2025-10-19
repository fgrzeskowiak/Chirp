package com.filippo.core.domain.auth

import kotlinx.coroutines.flow.Flow

interface SessionStorage {
    val session: Flow<Session?>
    suspend fun set(info: Session)
    suspend fun clear()
}
