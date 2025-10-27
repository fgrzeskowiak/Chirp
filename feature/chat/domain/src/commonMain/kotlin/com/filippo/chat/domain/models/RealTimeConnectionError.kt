package com.filippo.chat.domain.models

import com.filippo.core.domain.Error

enum class RealTimeConnectionError: Error {
    NOT_CONNECTED,
    MESSAGE_SEND_FAILED,
}