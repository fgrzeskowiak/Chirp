package com.filippo.chirp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filippo.chat.presentation.navigation.ChatGraphRoutes
import com.filippo.core.domain.auth.SessionStorage
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel(sessionStorage: SessionStorage) : ViewModel() {

    val startDestination: StateFlow<Any?> = sessionStorage.session
        .map { ChatGraphRoutes.Graph }
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)
}
