package com.filippo.auth.domain.validation

import com.filippo.core.domain.Error

sealed interface ValidationError: Error {
    data object Username: ValidationError
    data object Email: ValidationError
    data object Password: ValidationError
}
