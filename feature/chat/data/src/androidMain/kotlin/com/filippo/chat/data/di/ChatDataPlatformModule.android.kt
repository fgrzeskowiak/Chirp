package com.filippo.chat.data.di

import android.content.Context
import com.filippo.chat.data.lifecycle.AndroidAppLifecycleObserver
import com.filippo.chat.data.lifecycle.AppLifecycleObserver
import com.filippo.chat.data.network.AndroidNetworkObserver
import com.filippo.chat.data.network.NetworkObserver
import com.filippo.chat.data.notifications.FirebasePushNotificationService
import com.filippo.chat.domain.PushNotificationService
import com.filippo.core.domain.logging.ChirpLogger
import jakarta.inject.Singleton
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module

@Module
@Configuration
actual class ChatDataPlatformModule {

    @Singleton
    fun networkObserver(context: Context): NetworkObserver = AndroidNetworkObserver(context)

    @Singleton
    fun appLifecycleObserver(): AppLifecycleObserver = AndroidAppLifecycleObserver()

    @Singleton
    fun pushNotificationService(logger: ChirpLogger): PushNotificationService =
        FirebasePushNotificationService(logger)
}