package com.filippo.chirp.notifications

import com.filippo.chat.data.notifications.IosDeviceTokenHolder

object IosDeviceTokenHolderBridge {
    fun updateToken(token: String) {
        IosDeviceTokenHolder.updateToken(token)
    }
}