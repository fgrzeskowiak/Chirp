package com.filippo.chat.presentation.mappers

import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.network_error
import chirp.feature.chat.presentation.generated.resources.offline
import chirp.feature.chat.presentation.generated.resources.online
import chirp.feature.chat.presentation.generated.resources.reconnecting
import chirp.feature.chat.presentation.generated.resources.unknown_error
import com.filippo.chat.domain.models.ConnectionState
import com.filippo.core.presentation.util.UiText

fun ConnectionState.toUiText(): UiText = UiText.Resource(
    when (this) {
        ConnectionState.DISCONNECTED -> Res.string.offline
        ConnectionState.CONNECTING -> Res.string.reconnecting
        ConnectionState.CONNECTED -> Res.string.online
        ConnectionState.ERROR_NETWORK -> Res.string.network_error
        ConnectionState.ERROR_UNKNOWN -> Res.string.unknown_error
    }
)
