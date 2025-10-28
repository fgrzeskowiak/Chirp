package com.filippo.chat.data.di

import com.filippo.chat.data.lifecycle.AppLifecycleObserver
import com.filippo.chat.data.lifecycle.IosAppLifecycleObserver
import com.filippo.chat.data.network.IosNetworkObserver
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
    fun networkObserver(): NetworkObserver = IosNetworkObserver()

    @Singleton
    fun appLifecycleObserver(): AppLifecycleObserver = IosAppLifecycleObserver()

    @Singleton
    fun pushNotificationService(): PushNotificationService = FirebasePushNotificationService()
}