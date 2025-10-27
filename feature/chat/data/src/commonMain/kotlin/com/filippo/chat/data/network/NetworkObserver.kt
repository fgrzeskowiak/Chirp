package com.filippo.chat.data.network

import kotlinx.coroutines.flow.Flow

interface NetworkObserver {
    val isNetworkAvailable: Flow<Boolean>
}