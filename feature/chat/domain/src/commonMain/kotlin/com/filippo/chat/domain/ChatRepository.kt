package com.filippo.chat.domain

import com.filippo.chat.domain.models.Chat
import com.filippo.chat.domain.models.ChatDetails
import com.filippo.core.domain.RemoteResult
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    val chats: Flow<List<Chat>>
    fun getChatDetails(id: String): Flow<ChatDetails>
    suspend fun fetchChats(): RemoteResult<List<Chat>>
    suspend fun fetchChat(id: String): RemoteResult<Chat>
    suspend fun createChat(participants: List<String>): RemoteResult<Chat>
    suspend fun leaveChat(chatId: String): RemoteResult<Unit>
    suspend fun addParticipants(chatId: String, participantIds: List<String>): RemoteResult<Chat>
}