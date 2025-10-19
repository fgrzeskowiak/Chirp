package com.filippo.chirp.core.data.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.filippo.chirp.core.data.mapper.toDomain
import com.filippo.chirp.core.data.mapper.toResponse
import com.filippo.chirp.core.data.model.LoginResponse
import com.filippo.core.domain.auth.Session
import com.filippo.core.domain.auth.SessionStorage
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

@Singleton
class DatastoreSessionStorage(
    private val datastore: DataStore<Preferences>,
    private val json: Json,
) : SessionStorage {
    private val authInfoKey = stringPreferencesKey(SESSION_KEY)

    override val session: Flow<Session?> = datastore
        .data
        .map { prefs ->
            prefs[authInfoKey]
                ?.let { json.decodeFromString<LoginResponse>(it) }
                ?.toDomain()
        }

    override suspend fun set(info: Session) {
        datastore.edit { prefs ->
            prefs[authInfoKey] = json.encodeToString(info.toResponse())
        }
    }

    override suspend fun clear() {
        datastore.edit { it.remove(authInfoKey) }
    }
}

private const val SESSION_KEY = "key_session"
