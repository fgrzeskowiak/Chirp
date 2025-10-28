package com.filippo.chat.domain

import com.filippo.core.domain.RemoteResult

interface DeviceTokenService {
    suspend fun register(token: String, platform: String): RemoteResult<Unit>
    suspend fun unregister(token: String): RemoteResult<Unit>
}