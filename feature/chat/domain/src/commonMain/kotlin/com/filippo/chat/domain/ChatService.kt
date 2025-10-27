package com.filippo.chat.domain

import com.filippo.chat.domain.models.Chat
import com.filippo.core.domain.RemoteResult

interface ChatService {
    suspend fun createChat(participantIds: List<String>): RemoteResult<Chat>
    suspend fun getChats(): RemoteResult<List<Chat>>
    suspend fun getChat(id: String): RemoteResult<Chat>
    suspend fun leaveChat(id: String): RemoteResult<Unit>
    suspend fun addParticipants(chatId: String, participantIds: List<String>): RemoteResult<Chat>
}