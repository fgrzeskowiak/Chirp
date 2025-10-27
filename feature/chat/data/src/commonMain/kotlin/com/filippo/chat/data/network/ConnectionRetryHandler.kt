package com.filippo.chat.data.network

import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlin.math.pow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.times

class ConnectionRetryHandler @Inject constructor(
    private val errorHandler: ConnectionErrorHandler,
) {
    private var skipBackoff = false
    private val maxDelay = 30.seconds

    fun shouldRetry(error: Throwable, attempt: Long): Boolean =
        errorHandler.isRetriableError(error)

    suspend fun applyRetryDelay(attempt: Long) {
        if (skipBackoff) {
            skipBackoff = false
        } else {
            delay(createBackoffDelay(attempt.toInt()))
        }
    }

    fun resetDelay() {
        skipBackoff = true
    }

    private fun createBackoffDelay(attempt: Int): Duration {
        val delayTime = 2.0.pow(attempt) * 2.seconds
        return delayTime.coerceAtMost(maxDelay)
    }
}