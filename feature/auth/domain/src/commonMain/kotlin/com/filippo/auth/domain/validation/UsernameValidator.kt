package com.filippo.auth.domain.validation

import arrow.core.raise.either
import arrow.core.raise.ensure

object UsernameValidator {
    fun validate(username: String): ValidationResult = either {
        ensure(username.length in 3..20) { ValidationError.Username }
        username
    }
}
