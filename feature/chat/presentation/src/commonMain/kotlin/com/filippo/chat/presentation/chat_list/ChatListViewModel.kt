package com.filippo.chat.presentation.chat_list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class ChatListViewModel() : ViewModel() {

    private val state = MutableStateFlow(ChatListState())
    val uiState = state.asStateFlow()

    fun onAction(action: ChatListAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }
}
