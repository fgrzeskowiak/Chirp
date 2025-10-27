package com.filippo.chirp.core.data.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.filippo.chirp.core.data.networking.HttpClientFactory
import jakarta.inject.Singleton
import kotlinx.serialization.json.Json
import okio.Path.Companion.toPath
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named

@Module
@Configuration
@ComponentScan("com.filippo.chirp.core.data")
class CoreDataModule {

    @Singleton
    fun json() = Json {
        ignoreUnknownKeys = true
    }

    @Singleton
    fun sessionDatastore(
        @Named(DATASTORE_FILE_NAME) path: String,
    ) = PreferenceDataStoreFactory.createWithPath { path.toPath() }

    @Singleton
    fun httpClient(httpClientFactory: HttpClientFactory) = httpClientFactory.create()

    @Factory
    @Named(WEB_SOCKET_BASE_URL)
    fun webSocketBaseUrl() = "wss://chirp.pl-coding.com/ws"

}
const val WEB_SOCKET_BASE_URL = "web_socket_base_url"
