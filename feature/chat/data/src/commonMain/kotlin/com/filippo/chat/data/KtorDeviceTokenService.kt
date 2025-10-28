package com.filippo.chat.data

import com.filippo.chat.data.model.RegisterDeviceTokenRequest
import com.filippo.chat.domain.DeviceTokenService
import com.filippo.chirp.core.data.networking.delete
import com.filippo.chirp.core.data.networking.post
import com.filippo.core.domain.RemoteResult
import io.ktor.client.HttpClient
import jakarta.inject.Inject

class KtorDeviceTokenService @Inject constructor(
    private val httpClient: HttpClient
): DeviceTokenService {

    override suspend fun register(
        token: String,
        platform: String
    ): RemoteResult<Unit> {
        return httpClient.post(
            route = "/notification/register",
            body = RegisterDeviceTokenRequest(
                token = token,
                platform = platform
            )
        )
    }

    override suspend fun unregister(token: String): RemoteResult<Unit> {
        return httpClient.delete(
            route = "/notification/$token"
        )
    }
}