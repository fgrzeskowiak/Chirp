package com.filippo.chat.presentation.manage_chat

import androidx.compose.foundation.text.input.TextFieldState
import com.filippo.chat.presentation.chat_list_details.model.ChatParticipantUiModel
import com.filippo.core.presentation.util.UiText

data class ManageChatState(
    val query: TextFieldState = TextFieldState(),
    val selectedParticipants: List<ChatParticipantUiModel> = emptyList(),
    val existingParticipants: List<ChatParticipantUiModel> = emptyList(),
    val isSearching: Boolean = false,
    val isCreatingChat: Boolean = false,
    val canAddParticipant: Boolean = false,
    val searchResult: ChatParticipantUiModel? = null,
    val searchError: UiText? = null,
    val createChatError: UiText? = null,
)