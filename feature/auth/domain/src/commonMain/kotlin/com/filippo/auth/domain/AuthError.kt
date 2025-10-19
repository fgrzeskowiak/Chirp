package com.filippo.auth.domain

import com.filippo.auth.domain.validation.ValidationError
import com.filippo.core.domain.DataError

sealed interface AuthError {
    data class Request(val error: DataError.Remote) : AuthError
    data class Validation(val validationErrors: List<ValidationError>) : AuthError
}
