package com.filippo.chirp.core.data.networking

import arrow.core.Either
import com.filippo.core.domain.DataError
import com.filippo.core.domain.RemoteResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse

expect suspend inline fun <reified Response> apiCall(
    call: suspend () -> HttpResponse,
): RemoteResult<Response>

suspend inline fun <reified Request, reified Response : Any> HttpClient.post(
    route: String,
    body: Request,
    params: Map<String, Any> = emptyMap(),
    builder: HttpRequestBuilder.() -> Unit = {},
): RemoteResult<Response> =
    apiCall {
        post {
            url(route)
            params.forEach { (key, value) -> parameter(key, value) }
            setBody(body)
            builder()
        }
    }

suspend inline fun <reified Request, reified Response : Any> HttpClient.put(
    route: String,
    body: Request,
    params: Map<String, Any> = emptyMap(),
    builder: HttpRequestBuilder.() -> Unit = {},
): RemoteResult<Response> =
    apiCall {
        put {
            url(route)
            params.forEach { (key, value) -> parameter(key, value) }
            setBody(body)
            builder()
        }
    }

suspend inline fun <reified Response : Any> HttpClient.get(
    route: String,
    params: Map<String, Any> = emptyMap(),
    builder: HttpRequestBuilder.() -> Unit = {},
): RemoteResult<Response> =
    apiCall {
        get {
            url(route)
            params.forEach { (key, value) -> parameter(key, value) }
            builder()
        }
    }

suspend inline fun <reified Response : Any> HttpClient.delete(
    route: String,
    params: Map<String, Any> = emptyMap(),
    builder: HttpRequestBuilder.() -> Unit = {},
): RemoteResult<Response> =
    apiCall {
        delete {
            url(route)
            params.forEach { (key, value) -> parameter(key, value) }
            builder()
        }
    }


suspend inline fun <reified Response> parseResponse(
    response: HttpResponse,
): RemoteResult<Response> =
    when (response.status.value) {
        in 200..299 -> Either.catch { response.body<Response>() }
            .mapLeft { DataError.Remote.SERIALIZATION }

        400 -> Either.Left(DataError.Remote.BAD_REQUEST)
        401 -> Either.Left(DataError.Remote.UNAUTHORIZED)
        403 -> Either.Left(DataError.Remote.FORBIDDEN)
        404 -> Either.Left(DataError.Remote.NOT_FOUND)
        408 -> Either.Left(DataError.Remote.REQUEST_TIMEOUT)
        409 -> Either.Left(DataError.Remote.CONFLICT)
        413 -> Either.Left(DataError.Remote.PAYLOAD_TOO_LARGE)
        429 -> Either.Left(DataError.Remote.TOO_MANY_REQUESTS)
        500 -> Either.Left(DataError.Remote.SERVER_ERROR)
        503 -> Either.Left(DataError.Remote.SERVICE_UNAVAILABLE)
        else -> Either.Left(DataError.Remote.UNKNOWN)
    }
