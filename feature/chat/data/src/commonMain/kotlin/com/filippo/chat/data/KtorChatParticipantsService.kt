package com.filippo.chat.data

import com.filippo.chat.data.mappers.toDomain
import com.filippo.chat.data.model.ChatParticipantResponse
import com.filippo.chat.domain.ChatParticipantsService
import com.filippo.chat.domain.models.ChatParticipant
import com.filippo.chirp.core.data.networking.get
import com.filippo.core.domain.RemoteResult
import io.ktor.client.HttpClient
import jakarta.inject.Inject

internal class KtorChatParticipantsService @Inject constructor(
    private val client: HttpClient,
) : ChatParticipantsService {

    override suspend fun searchParticipant(query: String): RemoteResult<ChatParticipant> =
        client.get<ChatParticipantResponse>(
            route = "/participants",
            params = mapOf("query" to query)
        ).map { it.toDomain() }
}