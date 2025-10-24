package com.filippo.chat.presentation.chat_list_details

sealed interface ChatListDetailsAction {
    data class OnChatClick(val chatId: String?) : ChatListDetailsAction
    data object OnProfileDetailsClick : ChatListDetailsAction
    data object OnCreateChatClick : ChatListDetailsAction
    data object OnManageChatClick : ChatListDetailsAction
    data object OnDismissCurrentDialog : ChatListDetailsAction
}