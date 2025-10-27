package com.filippo.chat.presentation.chat_list_details.details

import androidx.compose.foundation.text.input.clearText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filippo.chat.domain.ChatRepository
import com.filippo.chat.presentation.mappers.toUiModel
import com.filippo.core.domain.auth.SessionStorage
import com.filippo.core.presentation.util.toUiText
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class ChatDetailsViewModel(
    private val repository: ChatRepository,
    sessionStorage: SessionStorage,
) : ViewModel() {

    private val state = MutableStateFlow(ChatDetailsState())
    private val currentChatId = MutableStateFlow<String?>(null)

    private val events = Channel<ChatDetailsEvent>()
    val uiEvents = events.receiveAsFlow()

    private val chatDetails = currentChatId
        .flatMapLatest { chatId ->
            if (chatId != null) {
                fetchChat(chatId)
                repository.getChatDetails(chatId)
            } else {
                flowOf(null)
            }
        }

    val uiState = combine(
        state,
        chatDetails,
        sessionStorage.session.mapNotNull { it?.user },
    ) { currentState, chatDetails, user ->
        currentState.copy(
            chat = chatDetails?.chat?.toUiModel(user.id),
            messages = emptyList(),
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, ChatDetailsState())

    fun onAction(action: ChatDetailsAction) {
        when (action) {
            is ChatDetailsAction.OnLeaveChatClick -> onLeaveChatClick()
            else -> Unit
        }
    }

    private fun onLeaveChatClick() {
        val chatId = currentChatId.value ?: return

        viewModelScope.launch {
            repository.leaveChat(chatId)
                .fold(
                    ifLeft = { events.send(ChatDetailsEvent.OnError(it.toUiText())) },
                    ifRight = {
                        this@ChatDetailsViewModel.currentChatId.value = null
                        state.value.messageInput.clearText()
                        events.send(ChatDetailsEvent.OnChatLeft)
                    }
                )
        }
    }

    fun onChatSelected(chatId: String?) {
        this.currentChatId.value = chatId
    }

    private fun fetchChat(chatId: String) {
        viewModelScope.launch {
            repository.fetchChat(chatId)
        }
    }
}