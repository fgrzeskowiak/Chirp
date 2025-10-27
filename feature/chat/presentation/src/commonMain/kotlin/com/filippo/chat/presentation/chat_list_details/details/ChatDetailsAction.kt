package com.filippo.chat.presentation.chat_list_details.details

import com.filippo.chat.presentation.chat_list_details.model.MessageUiModel

sealed interface ChatDetailsAction {
    data class OnDeleteMessageClick(val message: MessageUiModel.LocalUserMessage): ChatDetailsAction
    data class OnMessageLongClick(val message: MessageUiModel.LocalUserMessage): ChatDetailsAction
    data class OnRetryMessageClick(val message: MessageUiModel.LocalUserMessage): ChatDetailsAction
    data object OnSendMessageClick: ChatDetailsAction
    data object OnScrollToTop: ChatDetailsAction
    data object OnDismissMessageMenu: ChatDetailsAction
    data object OnBackClick: ChatDetailsAction
    data object OnLeaveChatClick: ChatDetailsAction
    data object OnDismissChatOptions: ChatDetailsAction
}