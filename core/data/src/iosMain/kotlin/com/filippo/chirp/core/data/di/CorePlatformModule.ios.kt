package com.filippo.chirp.core.data.di

import io.ktor.client.engine.darwin.Darwin
import jakarta.inject.Singleton
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import kotlin.experimental.ExperimentalNativeApi

@Module
@OptIn(ExperimentalForeignApi::class)
@Configuration
actual class CorePlatformModule {

    @Singleton
    @Named(DATASTORE_FILE_NAME)
    fun datastorePath(): String {
        val directory = NSFileManager.defaultManager
            .URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                error = null,
                create = false,
            )

        return requireNotNull(directory).path + "/${DATASTORE_FILE_NAME}"
    }

    @Singleton
    fun httpEngine() = Darwin.create()

    @Factory
    @Named(BASE_URL)
    @OptIn(ExperimentalNativeApi::class)
    fun baseUrl() = if (Platform.isDebugBinary) {
        "http://localhost:8085"
    } else {
        "https://chirp.pl-coding.com"
    }
}
