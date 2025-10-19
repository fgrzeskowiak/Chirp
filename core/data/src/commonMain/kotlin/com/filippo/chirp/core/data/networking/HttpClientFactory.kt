package com.filippo.chirp.core.data.networking

import com.filippo.chirp.core.data.BuildKonfig
import com.filippo.chirp.core.data.auth.DatastoreSessionStorage
import com.filippo.chirp.core.data.di.BASE_URL
import com.filippo.chirp.core.data.mapper.toDomain
import com.filippo.chirp.core.data.model.LoginResponse
import com.filippo.chirp.core.data.model.RefreshTokenRequest
import com.filippo.core.domain.auth.Session
import com.filippo.core.domain.logging.ChirpLogger
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.RefreshTokensParams
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.LoggingFormat
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.header
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import jakarta.inject.Inject
import jakarta.inject.Named
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json

class HttpClientFactory @Inject constructor(
    private val chirpLogger: ChirpLogger,
    private val sessionStorage: DatastoreSessionStorage,
    private val json: Json,
    private val engine: HttpClientEngine,
    @Named(BASE_URL) private val baseUrl: String,
) {
    fun create(): HttpClient =
        HttpClient(engine) {
            install(Logging) {
                level = LogLevel.ALL
                format = LoggingFormat.Default
                logger = object : Logger {
                    override fun log(message: String) {
                        chirpLogger.debug(message)
                    }
                }
            }
            install(ContentNegotiation) {
                json(json)
            }

            install(HttpTimeout) {
                socketTimeoutMillis = 20_000L
                requestTimeoutMillis = 20_000L
            }

            install(WebSockets) {
                pingIntervalMillis = 20_000L
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        sessionStorage.session
                            .firstOrNull()
                            ?.let { BearerTokens(it.accessToken, it.refreshToken) }
                    }

                    refreshTokens {
                        if (response.request.url.encodedPath.contains("auth/")) {
                            return@refreshTokens null
                        }

                        val authInfo = sessionStorage.session.firstOrNull()
                        if (authInfo?.refreshToken.isNullOrBlank()) {
                            sessionStorage.clear()
                            return@refreshTokens null
                        }

                        return@refreshTokens fetchNewTokens(authInfo)
                    }
                }
            }

            defaultRequest {
                url(baseUrl)
                header("x-api-key", BuildKonfig.API_KEY)
                contentType(ContentType.Application.Json)
            }
        }

    private suspend fun RefreshTokensParams.fetchNewTokens(authInfo: Session): BearerTokens? =
        client.post<RefreshTokenRequest, LoginResponse>(
            route = "/auth/refresh",
            body = RefreshTokenRequest(
                refreshToken = authInfo.refreshToken,
            ),
            builder = { markAsRefreshTokenRequest() }
        ).fold(
            ifLeft = {
                sessionStorage.clear()
                null
            },
            ifRight = {
                sessionStorage.set(it.toDomain())
                BearerTokens(it.accessToken, it.refreshToken)
            }
        )
}