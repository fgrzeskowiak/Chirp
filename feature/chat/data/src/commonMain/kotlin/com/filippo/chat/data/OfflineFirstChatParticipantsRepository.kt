package com.filippo.chat.data

import arrow.core.left
import arrow.core.right
import com.filippo.chat.data.mappers.toDomain
import com.filippo.chat.database.ChirpDatabase
import com.filippo.chat.domain.ChatParticipantsRepository
import com.filippo.chat.domain.ChatParticipantsService
import com.filippo.chat.domain.models.ChatParticipant
import com.filippo.core.domain.DataError
import com.filippo.core.domain.RemoteResult
import jakarta.inject.Inject

internal class OfflineFirstChatParticipantsRepository @Inject constructor(
    private val service: ChatParticipantsService,
    private val database: ChirpDatabase,
) : ChatParticipantsRepository {
    private val participantsDao get() = database.chatParticipantDao

    override suspend fun search(username: String): RemoteResult<ChatParticipant> {
        return participantsDao.getParticipantsByUsername(username)
            .firstOrNull()
            ?.toDomain()
            ?.right()
            ?: DataError.Remote.NOT_FOUND.left()
    }
}