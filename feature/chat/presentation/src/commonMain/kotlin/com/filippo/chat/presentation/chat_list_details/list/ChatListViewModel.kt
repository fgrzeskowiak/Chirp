package com.filippo.chat.presentation.chat_list_details.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filippo.chat.domain.ChatRepository
import com.filippo.chat.presentation.mappers.toUiModel
import com.filippo.core.domain.auth.SessionStorage
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ChatListViewModel(
    private val repository: ChatRepository,
    sessionStorage: SessionStorage,
) : ViewModel() {

    val uiState: StateFlow<ChatListState> = combine(
        repository.chats,
        sessionStorage.session.mapNotNull { it?.user },
    ) { chats, currentUser ->
        ChatListState(
            isLoading = false,
            chats = chats.map { it.toUiModel(currentUser.id) },
            currentUser = currentUser.toUiModel(),
        )
    }
        .onStart { fetchChats() }
        .stateIn(viewModelScope, SharingStarted.Lazily, ChatListState())

    private fun fetchChats() {
        viewModelScope.launch {
            repository.fetchChats()
        }
    }
}