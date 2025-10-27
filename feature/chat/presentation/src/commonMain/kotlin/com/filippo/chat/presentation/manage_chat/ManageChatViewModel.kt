package com.filippo.chat.presentation.manage_chat

import androidx.compose.foundation.text.input.clearText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filippo.chat.domain.ChatRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam

@KoinViewModel
class ManageChatViewModel(
    @InjectedParam private val chatId: String,
    private val repository: ChatRepository,
) : ViewModel() {

    private val membersAddedTrigger = Channel<Unit>()
    val membersAdded = membersAddedTrigger.receiveAsFlow()

    private val state = MutableStateFlow(ManageChatState())
    val uiState = state.asStateFlow()

    fun onAddParticipantClick() {
        state.update { currentState ->
            val participantToAdd = currentState.searchResult ?: return
            val isAlreadySelected = currentState.selectedParticipants.any { it.id == participantToAdd.id }
            val isAlreadyInChat = currentState.existingParticipants.any { it.id == participantToAdd.id }

            val updatedMembers = if (isAlreadySelected || isAlreadyInChat) {
                currentState.selectedParticipants
            } else {
                currentState.selectedParticipants + participantToAdd
            }

            currentState.query.clearText()

            currentState.copy(
                selectedParticipants = updatedMembers,
                canAddParticipant = false,
                searchResult = null
            )
        }
    }

    fun onSaveClick() {
        viewModelScope.launch {
            repository.addParticipants(
                chatId = chatId,
                participantIds = state.value.selectedParticipants.map { it.id },
            ).fold(
                ifLeft = { },
                ifRight = { }
            )
        }
    }
}