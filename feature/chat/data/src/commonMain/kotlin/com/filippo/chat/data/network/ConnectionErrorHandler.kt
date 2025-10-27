package com.filippo.chat.data.network

interface ConnectionErrorHandler {
    fun isRetriableError(error: Throwable): Boolean
}