package com.filippo.chat.presentation.chat_list_details

import androidx.lifecycle.ViewModel
import com.filippo.chat.presentation.chat_list_details.ChatListDetailsState.DialogState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ChatListDetailsViewModel : ViewModel() {

    private val state = MutableStateFlow(ChatListDetailsState())
    val uiState: StateFlow<ChatListDetailsState> = state

    fun onAction(action: ChatListDetailsAction) {
        state.update {
            when (action) {
                is ChatListDetailsAction.OnChatClick -> it.copy(selectedChatId = action.chatId)
                ChatListDetailsAction.OnCreateChatClick -> it.copy(dialogState = DialogState.CreateChat)
                ChatListDetailsAction.OnDismissCurrentDialog -> it.copy(dialogState = DialogState.Hidden)
                ChatListDetailsAction.OnProfileDetailsClick -> it.copy(dialogState = DialogState.Profile)
                ChatListDetailsAction.OnManageChatClick -> if (it.selectedChatId != null) {
                    it.copy(dialogState = DialogState.ManageChat(it.selectedChatId))
                } else {
                    it
                }
            }
        }
    }

}