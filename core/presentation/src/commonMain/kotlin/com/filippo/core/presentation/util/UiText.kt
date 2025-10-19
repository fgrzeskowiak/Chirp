package com.filippo.core.presentation.util

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource

sealed interface UiText {
    data class Dynamic(val value: String) : UiText
    data class Resource(
        val id: StringResource,
        val args: Array<Any> = emptyArray(),
    ) : UiText

    @Composable
    fun asString(): String = when (this) {
        is Dynamic -> value
        is Resource -> stringResource(id, *args)
    }

    suspend fun asStringAsync(): String = when (this) {
        is Dynamic -> value
        is Resource -> getString(id)
    }
}
