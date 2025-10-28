package com.filippo.chat.data.notifications

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object IosDeviceTokenHolder {
    private val tokenFlow = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = tokenFlow.asStateFlow()

    fun updateToken(token: String) {
        tokenFlow.value = token
    }
}