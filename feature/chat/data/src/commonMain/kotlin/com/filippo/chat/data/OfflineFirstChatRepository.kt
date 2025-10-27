package com.filippo.chat.data

import androidx.room.immediateTransaction
import androidx.room.useWriterConnection
import com.filippo.chat.data.mappers.toDomain
import com.filippo.chat.data.mappers.toEntity
import com.filippo.chat.database.ChirpDatabase
import com.filippo.chat.database.model.ChatEntity
import com.filippo.chat.database.model.ChatMessageEntity
import com.filippo.chat.database.model.ChatParticipantCrossRef
import com.filippo.chat.database.model.ChatParticipantEntity
import com.filippo.chat.domain.ChatRepository
import com.filippo.chat.domain.ChatService
import com.filippo.chat.domain.di.Mocked
import com.filippo.chat.domain.models.Chat
import com.filippo.chat.domain.models.ChatDetails
import com.filippo.core.domain.RemoteResult
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

internal class OfflineFirstChatRepository @Inject constructor(
    @Mocked private val chatService: ChatService,
    private val database: ChirpDatabase,
) : ChatRepository {

    private val chatDao get() = database.chatDao
    private val participantsDao get() = database.chatParticipantDao
    private val messagesDao get() = database.chatMessageDao
    private val crossRefDao get() = database.crossRefDao

    override val chats: Flow<List<Chat>> = chatDao
        .getChatsWithParticipants()
        .map { chats -> chats.map { it.toDomain() } }

    override fun getChatDetails(id: String): Flow<ChatDetails> = chatDao
        .getChatInfo(id)
        .filterNotNull()
        .map { it.toDomain() }

    override suspend fun fetchChats(): RemoteResult<List<Chat>> =
        chatService.getChats()
            .onRight { syncChats(it) }

    override suspend fun fetchChat(id: String): RemoteResult<Chat> =
        chatService.getChat(id)
            .onRight { persistChat(it) }

    override suspend fun createChat(participants: List<String>): RemoteResult<Chat> =
        chatService.createChat(participants)
            .onRight { persistChat(it) }

    override suspend fun leaveChat(chatId: String): RemoteResult<Unit> =
        chatService.leaveChat(chatId)
            .onRight { chatDao.delete(chatId) }

    override suspend fun addParticipants(
        chatId: String,
        participantIds: List<String>
    ): RemoteResult<Chat> =
        chatService.addParticipants(chatId, participantIds)
            .onRight { persistChat(it) }

    private suspend fun persistChat(chat: Chat) {
        val participantIds = mutableListOf<String>()
        val participants = mutableListOf<ChatParticipantEntity>()
        val crossRefs = mutableListOf<ChatParticipantCrossRef>()

        for (participant in chat.participants) {
            participantIds.add(participant.userId)
            participants.add(participant.toEntity())
            crossRefs.add(
                ChatParticipantCrossRef(
                    chatId = chat.id,
                    userId = participant.userId,
                )
            )
        }
        database.useWriterConnection { transactor ->
            transactor.immediateTransaction {
                chatDao.upsert(chat.toEntity())
                participantsDao.upsert(participants)
                crossRefDao.upsert(crossRefs)
                val staleParticipants = crossRefDao.getAllChatParticipantIds(chat.id) - participantIds
                crossRefDao.deleteByUserId(staleParticipants)
            }
        }
    }

    private suspend fun syncChats(serverChats: List<Chat>) {
        val chatIds = mutableListOf<String>()
        val chats = mutableListOf<ChatEntity>()
        val participantIds = mutableListOf<String>()
        val participants = mutableListOf<ChatParticipantEntity>()
        val messages = mutableListOf<ChatMessageEntity>()
        val crossRefs = mutableListOf<ChatParticipantCrossRef>()

        for (chat in serverChats) {
            chatIds.add(chat.id)
            chats.add(chat.toEntity())
            chat.lastMessage?.let { messages.add(it.toEntity()) }
            for (participant in chat.participants) {
                participantIds.add(participant.userId)
                participants.add(participant.toEntity())
                crossRefs.add(
                    ChatParticipantCrossRef(
                        chatId = chat.id,
                        userId = participant.userId,
                    )
                )
            }
        }
        database.useWriterConnection { transactor ->
            transactor.immediateTransaction {
                val staleChatIds = chatDao.getAllChatIds() - chatIds
                val staleCrossRefs = crossRefDao.getAll() - crossRefs
                chatDao.upsert(chats)
                chatDao.delete(staleChatIds)
                messagesDao.upsert(messages)
                participantsDao.upsert(participants)
                crossRefDao.upsert(crossRefs)
                crossRefDao.delete(staleCrossRefs)
            }
        }
    }
}