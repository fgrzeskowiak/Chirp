package com.filippo.chat.presentation.chat_list_details

data class ChatListDetailsState(
    val selectedChatId: String? = null,
    val dialogState: DialogState = DialogState.Hidden,
) {
    sealed interface DialogState {
        data object Hidden : DialogState
        data object CreateChat : DialogState
        data object Profile : DialogState
        data class ManageChat(val chatId: String) : DialogState
    }
}