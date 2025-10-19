package com.filippo.auth.presentation.mapper

import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.error_invalid_email
import chirp.feature.auth.presentation.generated.resources.error_invalid_password
import chirp.feature.auth.presentation.generated.resources.error_invalid_username
import com.filippo.auth.domain.validation.ValidationError
import com.filippo.core.presentation.util.UiText

fun ValidationError.toUiText() = UiText.Resource(
    when (this) {
        ValidationError.Email -> Res.string.error_invalid_email
        ValidationError.Password -> Res.string.error_invalid_password
        ValidationError.Username -> Res.string.error_invalid_username
    }
)
