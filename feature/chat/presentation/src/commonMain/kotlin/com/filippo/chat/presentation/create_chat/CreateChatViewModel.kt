package com.filippo.chat.presentation.create_chat

import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.error_participant_not_found
import com.filippo.chat.domain.ChatParticipantsRepository
import com.filippo.chat.domain.ChatRepository
import com.filippo.chat.domain.models.Chat
import com.filippo.chat.presentation.mappers.toUiModel
import com.filippo.core.domain.DataError
import com.filippo.core.presentation.util.UiText
import com.filippo.core.presentation.util.toUiText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import kotlin.time.Duration.Companion.seconds

@KoinViewModel
@OptIn(FlowPreview::class)
class CreateChatViewModel(
    private val participantsRepository: ChatParticipantsRepository,
    private val chatRepository: ChatRepository,
) : ViewModel() {

    private val state = MutableStateFlow(CreateChatState())
    val uiState: StateFlow<CreateChatState> = state

    private val chatCreatedTrigger = Channel<Chat>()
    val chatCreated: Flow<Chat> = chatCreatedTrigger.receiveAsFlow()

    init {
        snapshotFlow { state.value.query.text.toString() }
            .debounce(1.seconds)
            .onEach(::performSearch)
            .launchIn(viewModelScope)
    }

    fun onAction(action: CreateChatAction) {
        when (action) {
            CreateChatAction.OnAddClick -> addParticipant()
            CreateChatAction.OnCreateChatClick -> createChat()
        }
    }

    private fun addParticipant() = with(state.value) {
        searchResult?.let { result ->
            val isAlreadyInChat = participants.any { it.id == result.id }
            if (!isAlreadyInChat) {
                state.update {
                    it.query.clearText()
                    it.copy(
                        participants = participants + result,
                        canAddParticipant = false,
                        searchResult = null
                    )
                }
            }
        }
    }

    private fun createChat() {
        val userIds = state.value.participants.map { it.id }
        if (userIds.isEmpty()) return

        viewModelScope.launch {
            state.update {
                it.copy(
                    isCreatingChat = true,
                    canAddParticipant = false,
                )
            }

            chatRepository.createChat(userIds)
                .fold(
                    ifLeft = { error ->
                        state.update {
                            it.copy(
                                createChatError = error.toUiText(),
                                canAddParticipant = it.searchResult != null && !it.isSearching,
                            )
                        }
                    },
                    ifRight = { chat ->
                        state.update {
                            it.copy(
                                isCreatingChat = false,
                            )
                        }
                        chatCreatedTrigger.send(chat)
                    },
                )
        }
    }

    private fun performSearch(query: String) {
        if (query.isBlank()) {
            state.update {
                it.copy(
                    searchResult = null,
                    canAddParticipant = false,
                    searchError = null
                )
            }
            return
        }

        viewModelScope.launch {
            state.update {
                it.copy(
                    isSearching = true,
                    canAddParticipant = false,
                )
            }

            participantsRepository.searchParticipant(query)
                .fold(
                    ifLeft = { error ->
                        val errorMessage = when (error) {
                            DataError.Remote.NOT_FOUND -> UiText.Resource(Res.string.error_participant_not_found)
                            else -> error.toUiText()
                        }
                        state.update {
                            it.copy(
                                searchError = errorMessage,
                                isSearching = false,
                                canAddParticipant = false,
                                searchResult = null,
                            )
                        }
                    },
                    ifRight = { participant ->
                        state.update {
                            it.copy(
                                isSearching = false,
                                searchResult = participant.toUiModel(),
                                canAddParticipant = true,
                                searchError = null,
                            )
                        }
                    }
                )
        }
    }
}