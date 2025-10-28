package com.filippo.chat.data.notifications

import com.filippo.chat.domain.DeviceTokenService
import com.filippo.core.domain.auth.SessionStorage
import com.google.firebase.messaging.FirebaseMessagingService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class ChirpFirebaseMessagingService : FirebaseMessagingService() {
    private val tokenService by inject<DeviceTokenService>()
    private val sessionStorage by inject<SessionStorage>()
    private val applicationScope by inject<CoroutineScope>()

    override fun onNewToken(token: String) {
        applicationScope.launch {
            sessionStorage.session.firstOrNull()?.let {
                tokenService.register(
                    token = token,
                    platform = "Android"
                )
            }
        }
    }
}