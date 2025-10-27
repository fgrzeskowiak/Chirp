package com.filippo.chat.domain

import com.filippo.chat.domain.models.ChatParticipant
import com.filippo.core.domain.RemoteResult

interface ChatParticipantsRepository {
    suspend fun search(username: String): RemoteResult<ChatParticipant>
}