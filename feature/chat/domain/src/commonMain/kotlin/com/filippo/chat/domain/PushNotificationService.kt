package com.filippo.chat.domain

import kotlinx.coroutines.flow.Flow

interface PushNotificationService {
    val deviceToken: Flow<String?>
}