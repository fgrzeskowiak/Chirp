package com.filippo.chirp.core.data.networking

import arrow.core.Either
import arrow.core.flatMap
import com.filippo.core.domain.DataError
import com.filippo.core.domain.RemoteResult
import io.ktor.client.statement.HttpResponse
import io.ktor.network.sockets.SocketTimeoutException
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import java.net.ConnectException
import java.net.UnknownHostException

actual suspend inline fun <reified Response> apiCall(
    call: suspend () -> HttpResponse,
): RemoteResult<Response> = Either.catch { call() }
    .flatMap {
        parseResponse<Response>(it)
    }
    .mapLeft {
        when (it) {
            is UnknownHostException, is UnresolvedAddressException, is ConnectException ->
                DataError.Remote.NO_INTERNET

            is SocketTimeoutException -> DataError.Remote.REQUEST_TIMEOUT
            is SerializationException -> DataError.Remote.SERIALIZATION
            else -> DataError.Remote.UNKNOWN
        }
    }
