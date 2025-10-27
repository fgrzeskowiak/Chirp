package com.filippo.chat.data

import com.filippo.chat.data.mappers.toDomain
import com.filippo.chat.data.model.ChatResponse
import com.filippo.chat.data.model.CreateChatRequest
import com.filippo.chat.data.model.ParticipantsRequest
import com.filippo.chat.domain.ChatService
import com.filippo.chat.domain.models.Chat
import com.filippo.chirp.core.data.networking.delete
import com.filippo.chirp.core.data.networking.get
import com.filippo.chirp.core.data.networking.post
import com.filippo.core.domain.RemoteResult
import io.ktor.client.HttpClient
import jakarta.inject.Inject
import kotlin.uuid.ExperimentalUuidApi

internal class KtorChatService @Inject constructor(
    private val client: HttpClient,
) : ChatService {

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun createChat(participantIds: List<String>): RemoteResult<Chat> =
        client.post<CreateChatRequest, ChatResponse>(
            route = "/chat",
            body = CreateChatRequest(otherUserIds = participantIds)
        ).map { it.toDomain() }

    override suspend fun getChats(): RemoteResult<List<Chat>> =
        client.get<List<ChatResponse>>(
            route = "/chat",
        ).map { response -> response.map { it.toDomain() } }

    override suspend fun getChat(id: String): RemoteResult<Chat> =
        client.get<ChatResponse>(
            route = "chat/$id",
        ).map { it.toDomain() }

    override suspend fun leaveChat(id: String): RemoteResult<Unit> =
        client.delete(
            route = "/chat/$id/leave",
        )

    override suspend fun addParticipants(
        chatId: String,
        participantIds: List<String>,
    ): RemoteResult<Chat> =
        client.post<ParticipantsRequest, ChatResponse>(
            route = "/chat/$chatId/add",
            body = ParticipantsRequest(participantIds)
        ).map { it.toDomain() }
}