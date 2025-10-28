package com.filippo.chirp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filippo.auth.presentation.navigation.AuthGraphRoutes
import com.filippo.chat.domain.DeviceTokenService
import com.filippo.chat.domain.PushNotificationService
import com.filippo.chat.presentation.navigation.ChatGraphRoutes
import com.filippo.core.domain.auth.SessionStorage
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel(
    sessionStorage: SessionStorage,
    pushNotificationService: PushNotificationService,
    deviceTokenService: DeviceTokenService,
) : ViewModel() {

    val startDestination: StateFlow<Any?> = sessionStorage.session
        .map { if (it == null) AuthGraphRoutes.Graph else ChatGraphRoutes.Graph }
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    init {
        combine(
            sessionStorage.session.filterNotNull(),
            pushNotificationService.deviceToken.distinctUntilChanged().filterNotNull()
        ) { session, token ->
            deviceTokenService.register(token, "ANDROID")
        }.launchIn(viewModelScope)
    }
}
