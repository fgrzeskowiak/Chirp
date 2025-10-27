package com.filippo.chat.data.lifecycle

import kotlinx.coroutines.flow.Flow

interface AppLifecycleObserver {
    val isInForeground: Flow<Boolean>
}