package com.filippo.chat.presentation.chat_list_details.list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ChatListViewModel : ViewModel() {

    private val state = MutableStateFlow(ChatListState())
    val uiState: StateFlow<ChatListState> = state
}