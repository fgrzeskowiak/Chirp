package com.filippo.chat.data

import arrow.core.Either
import com.filippo.chat.data.mappers.toDomain
import com.filippo.chat.data.model.ChatParticipantResponse
import com.filippo.chat.domain.ChatParticipantsRepository
import com.filippo.chat.domain.models.ChatParticipant
import com.filippo.chirp.core.data.networking.get
import com.filippo.core.domain.DataError
import com.filippo.core.domain.RemoteResult
import io.ktor.client.HttpClient
import jakarta.inject.Inject
import kotlinx.coroutines.delay

class KtorChatParticipantsRepository @Inject constructor(
    private val client: HttpClient,
): ChatParticipantsRepository {

    override suspend fun searchParticipant(query: String): RemoteResult<ChatParticipant> {
        delay(1000)
        return Either.Left(DataError.Remote.NOT_FOUND)
        client.get<ChatParticipantResponse>(
            route = "/participants",
            params = mapOf("query" to query)
        ).map { it.toDomain() }
    }
}