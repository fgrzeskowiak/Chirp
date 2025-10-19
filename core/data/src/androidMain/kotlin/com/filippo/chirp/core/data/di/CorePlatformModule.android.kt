package com.filippo.chirp.core.data.di

import android.content.Context
import com.filippo.chirp.core.data.BuildConfig
import io.ktor.client.engine.okhttp.OkHttp
import jakarta.inject.Singleton
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named

@Module
@Configuration
actual class CorePlatformModule {

    @Singleton
    @Named(DATASTORE_FILE_NAME)
    fun datastorePath(context: Context) =
        context.filesDir.resolve(DATASTORE_FILE_NAME).absolutePath

    @Singleton
    fun httpEngine() = OkHttp.create()

    @Factory
    @Named(BASE_URL)
    fun baseUrl() = if (BuildConfig.DEBUG) {
        "http://10.0.2.2:8085" // localhost alias for Android emulator
    } else {
        "https://chirp.pl-coding.com"
    }
}
