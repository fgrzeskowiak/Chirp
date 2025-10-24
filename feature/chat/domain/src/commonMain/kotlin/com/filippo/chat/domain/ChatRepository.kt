package com.filippo.chat.domain

import com.filippo.chat.domain.models.Chat
import com.filippo.core.domain.RemoteResult

interface ChatRepository {
    suspend fun createChat(participantIds: List<String>): RemoteResult<Chat>
}