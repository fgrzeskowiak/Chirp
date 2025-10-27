package com.filippo.chat.data.di

import android.content.Context
import com.filippo.chat.data.lifecycle.AndroidAppLifecycleObserver
import com.filippo.chat.data.lifecycle.AppLifecycleObserver
import com.filippo.chat.data.network.AndroidNetworkObserver
import com.filippo.chat.data.network.NetworkObserver
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
}