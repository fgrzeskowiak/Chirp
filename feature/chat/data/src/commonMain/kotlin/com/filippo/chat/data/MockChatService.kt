package com.filippo.chat.data

import arrow.core.left
import arrow.core.right
import com.filippo.chat.domain.ChatService
import com.filippo.chat.domain.di.Mocked
import com.filippo.chat.domain.models.Chat
import com.filippo.chat.domain.models.ChatMessage
import com.filippo.chat.domain.models.ChatParticipant
import com.filippo.chat.domain.models.MessageDeliveryStatus
import com.filippo.core.domain.DataError
import com.filippo.core.domain.RemoteResult
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Mocked
internal class MockChatService @Inject constructor() : ChatService {

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun createChat(participantIds: List<String>): RemoteResult<Chat> {
        delay(2000)
        return mockChats.find { it.participants.any { it.userId in participantIds.first() } }
            ?.copy(
                id = Uuid.random().toString(),
                lastActivityTimestamp = Clock.System.now(),
                lastMessage = null
            )
            ?.right()
            ?: DataError.Remote.NOT_FOUND.left()
    }

    override suspend fun getChats(): RemoteResult<List<Chat>> {
        delay(2000)
        return mockChats.right()
    }

    override suspend fun getChat(id: String): RemoteResult<Chat> {
        delay(2000)
        return mockChats.find { it.id == id }?.right() ?: DataError.Remote.NOT_FOUND.left()
    }

    override suspend fun leaveChat(id: String): RemoteResult<Unit> {
        return Unit.right()
    }

    override suspend fun addParticipants(
        chatId: String,
        participantIds: List<String>,
    ): RemoteResult<Chat> {
        delay(1000)
        val chat = mockChats.find { it.id == chatId }
        return chat
            ?.copy(
                participants = chat.participants.plus(
                    participantIds.map { id ->
                        ChatParticipant(
                            userId = id,
                            username = "New User $id",
                            profilePictureUrl = null
                        )
                    }
                )
            )
            ?.right()
            ?: DataError.Remote.NOT_FOUND.left()
    }

}

private val mockChats = List(4) { chatIndex ->
    val participants = List(chatIndex + 1) { userIndex ->
        ChatParticipant(
            userId = "$chatIndex$userIndex",
            username = "User $chatIndex$userIndex",
            profilePictureUrl = null
        )
    }
    Chat(
        id = chatIndex.toString(),
        participants = participants,
        lastActivityTimestamp = Clock.System.now().minus(chatIndex.hours),
        lastMessage = ChatMessage(
            id = chatIndex.toString(),
            chatId = chatIndex.toString(),
            content = "Last Message $chatIndex",
            createdAt = Clock.System.now().minus(chatIndex.hours),
            senderId = participants.first().userId,
            deliveryStatus = MessageDeliveryStatus.SENT
        )
    )
}