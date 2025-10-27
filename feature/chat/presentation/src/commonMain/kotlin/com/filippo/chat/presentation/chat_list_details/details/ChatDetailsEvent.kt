package com.filippo.chat.presentation.chat_list_details.details

import com.filippo.core.presentation.util.UiText

sealed interface ChatDetailsEvent {
    data object OnChatLeft: ChatDetailsEvent
    data class OnError(val error: UiText): ChatDetailsEvent
}