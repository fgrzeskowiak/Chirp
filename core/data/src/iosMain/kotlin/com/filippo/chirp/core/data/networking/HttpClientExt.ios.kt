package com.filippo.chirp.core.data.networking

import arrow.core.Either
import arrow.core.flatMap
import com.filippo.core.domain.DataError
import com.filippo.core.domain.RemoteResult
import io.ktor.client.engine.darwin.DarwinHttpRequestException
import io.ktor.client.statement.HttpResponse
import io.ktor.network.sockets.SocketTimeoutException
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import platform.Foundation.NSURLErrorCallIsActive
import platform.Foundation.NSURLErrorDNSLookupFailed
import platform.Foundation.NSURLErrorDataNotAllowed
import platform.Foundation.NSURLErrorDomain
import platform.Foundation.NSURLErrorInternationalRoamingOff
import platform.Foundation.NSURLErrorNetworkConnectionLost
import platform.Foundation.NSURLErrorNotConnectedToInternet
import platform.Foundation.NSURLErrorResourceUnavailable
import platform.Foundation.NSURLErrorTimedOut

actual suspend inline fun <reified Response> apiCall(
    call: suspend () -> HttpResponse,
): RemoteResult<Response> = Either.catch { call() }
    .flatMap { parseResponse<Response>(it) }
    .mapLeft {
        when (it) {
            is DarwinHttpRequestException -> handleDarwinException(it)
            is UnresolvedAddressException -> DataError.Remote.NO_INTERNET
            is SocketTimeoutException -> DataError.Remote.REQUEST_TIMEOUT
            is SerializationException -> DataError.Remote.SERIALIZATION
            else -> DataError.Remote.UNKNOWN
        }
    }

fun handleDarwinException(exception: DarwinHttpRequestException): DataError.Remote {
    val error = exception.origin

    return if (error.domain == NSURLErrorDomain) {
        when (error.code) {
            NSURLErrorNotConnectedToInternet,
            NSURLErrorNetworkConnectionLost,
            NSURLErrorDNSLookupFailed,
            NSURLErrorResourceUnavailable,
            NSURLErrorInternationalRoamingOff,
            NSURLErrorCallIsActive,
            NSURLErrorDataNotAllowed -> DataError.Remote.NO_INTERNET

            NSURLErrorTimedOut -> DataError.Remote.REQUEST_TIMEOUT
            else -> DataError.Remote.UNKNOWN
        }
    } else {
        DataError.Remote.UNKNOWN
    }
}
