package com.filippo.chat.data.network

import arrow.core.Either
import arrow.core.left
import com.filippo.chat.data.lifecycle.AppLifecycleObserver
import com.filippo.chat.data.model.WebSocketMessage
import com.filippo.chat.domain.models.ConnectionState
import com.filippo.chat.domain.models.RealTimeConnectionError
import com.filippo.chirp.core.data.di.WEB_SOCKET_BASE_URL
import com.filippo.core.domain.Error
import com.filippo.core.domain.auth.SessionStorage
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.bearerAuth
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import io.ktor.websocket.send
import jakarta.inject.Named
import jakarta.inject.Singleton
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.seconds

@Singleton
class WebSocketConnector(
    @Named(WEB_SOCKET_BASE_URL) private val baseUrl: String,
    private val httpClient: HttpClient,
    private val errorHandler: ConnectionErrorHandler,
    private val retryHandler: ConnectionRetryHandler,
    private val json: Json,
    sessionStorage: SessionStorage,
    lifecycleObserver: AppLifecycleObserver,
    networkObserver: NetworkObserver,
) {
    private val connectionState = MutableStateFlow(ConnectionState.DISCONNECTED)
    private var currentSession: WebSocketSession? = null

    private val isConnected = networkObserver.isNetworkAvailable
        .distinctUntilChanged()
        .debounce(1.seconds)

    private val isInForeground = lifecycleObserver.isInForeground
        .distinctUntilChanged()
        .onEach { if (it) retryHandler.resetDelay() }

    val messages = combine(
        sessionStorage.session,
        isConnected,
        isInForeground
    ) { session, isConnected, isInForeground ->
        session?.accessToken?.takeIf { isInForeground && isConnected }
    }.flatMapLatest { accessToken ->
        if (accessToken == null) {
            emptyFlow()
        } else {
            createSessionFlow(accessToken)
        }
    }

    suspend fun sendMessage(message: String): Either<Error, Unit> {
        val connectionState = connectionState.value
        val session = currentSession
        if (session == null || connectionState != ConnectionState.CONNECTED) {
            return RealTimeConnectionError.NOT_CONNECTED.left()
        }
        return Either.catch { session.send(message) }
            .mapLeft { RealTimeConnectionError.MESSAGE_SEND_FAILED }
    }

    private fun createSessionFlow(accessToken: String) =
        flow { emitAll(createWebSocketSession(accessToken).messages()) }
            .onCompletion {
                connectionState.value = ConnectionState.DISCONNECTED
                closeSession()
            }
            .retryWhen { error, attempt ->
                retryHandler.shouldRetry(error, attempt).also { shouldRetry ->
                    if (shouldRetry) {
                        connectionState.value = ConnectionState.CONNECTING
                        retryHandler.applyRetryDelay(attempt)
                    }
                }
            }
            .catch { nonRetriableError ->
                closeSession()
                throw nonRetriableError
            }

    private suspend fun createWebSocketSession(accessToken: String): WebSocketSession =
        httpClient.webSocketSession(baseUrl) { bearerAuth(accessToken) }
            .also(::currentSession::set)

    private fun WebSocketSession.messages() = incoming
        .consumeAsFlow()
        .buffer(100)
        .mapNotNull { frame ->
            when (frame) {
                is Frame.Text -> json.decodeFromString<WebSocketMessage>(frame.readText())
                is Frame.Ping -> {
                    send(Frame.Pong(frame.data))
                    null
                }

                else -> null
            }
        }

    private suspend fun closeSession() = withContext(NonCancellable) {
        currentSession?.close()
        currentSession = null
    }
}