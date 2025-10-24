package com.filippo.chat.presentation.chat_list_details.details

import androidx.compose.foundation.text.input.TextFieldState
import com.filippo.chat.presentation.chat_list_details.model.ChatUiModel
import com.filippo.chat.presentation.chat_list_details.model.MessageUiModel
import com.filippo.core.presentation.util.UiText

data class ChatDetailsState(
    val chat: ChatUiModel? = null,
    val messages: List<MessageUiModel> = emptyList(),
    val messageInput: TextFieldState = TextFieldState(),
    val dateBanner: Banner = Banner(),
    val isLoading: Boolean = false,
    val isPaginationLoading: Boolean = false,
    val canSend: Boolean = true,
    val endReached: Boolean = false,
    val error: UiText? = null,
    val paginationError: UiText? = null,
    val connectionError: UiText? = null,
) {
    data class Banner(
        val date: UiText? = null,
        val isVisible: Boolean = false,
    )
}