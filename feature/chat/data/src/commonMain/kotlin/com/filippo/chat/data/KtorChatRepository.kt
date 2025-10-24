package com.filippo.chat.data

import com.filippo.chat.data.mappers.toDomain
import com.filippo.chat.data.model.ChatResponse
import com.filippo.chat.data.model.CreateChatRequest
import com.filippo.chat.domain.ChatRepository
import com.filippo.chat.domain.models.Chat
import com.filippo.chirp.core.data.networking.post
import com.filippo.core.domain.RemoteResult
import io.ktor.client.HttpClient
import jakarta.inject.Inject

class KtorChatRepository @Inject constructor(
    private val client: HttpClient,
) : ChatRepository {

    override suspend fun createChat(participantIds: List<String>): RemoteResult<Chat> =
        client.post<CreateChatRequest, ChatResponse>(
            route = "/chat",
            body = CreateChatRequest(otherUserIds = participantIds)
        ).map { it.toDomain() }
}