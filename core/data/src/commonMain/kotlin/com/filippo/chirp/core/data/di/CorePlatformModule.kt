package com.filippo.chirp.core.data.di

import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module

@Module
@Configuration
expect class CorePlatformModule()
internal const val DATASTORE_FILE_NAME = "prefs.preferences_pb"
internal const val BASE_URL = "base_url"