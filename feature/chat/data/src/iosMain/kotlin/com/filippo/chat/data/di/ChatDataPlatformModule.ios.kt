package com.filippo.chat.data.di

import com.filippo.chat.data.lifecycle.AppLifecycleObserver
import com.filippo.chat.data.lifecycle.IosAppLifecycleObserver
import com.filippo.chat.data.network.IosNetworkObserver
import com.filippo.chat.data.network.NetworkObserver
import jakarta.inject.Singleton
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module

@Module
@Configuration
@ComponentScan("com.filippo.chat.data.di")
actual class ChatDataPlatformModule {

    @Singleton
    fun networkObserver(): NetworkObserver = IosNetworkObserver()

    @Singleton
    fun appLifecycleObserver(): AppLifecycleObserver = IosAppLifecycleObserver()
}