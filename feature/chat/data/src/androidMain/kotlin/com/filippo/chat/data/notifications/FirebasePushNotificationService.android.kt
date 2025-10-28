package com.filippo.chat.data.notifications

import com.filippo.chat.domain.PushNotificationService
import com.filippo.core.domain.logging.ChirpLogger
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.coroutineContext

internal class FirebasePushNotificationService(
    private val logger: ChirpLogger,
) : PushNotificationService {

    override val deviceToken: Flow<String?> = flow {
        try {
            emit(Firebase.messaging.token.await())
        } catch (e: Exception) {
            coroutineContext.ensureActive()
            logger.error("Failed to get FCM token", e)
            emit(null)
        }
    }
}