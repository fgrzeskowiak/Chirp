package com.filippo.auth.domain.validation

import arrow.core.raise.either
import arrow.core.raise.ensure

object EmailValidator {
    fun validate(email: String): ValidationResult = either {
        ensure(EMAIL_PATTERN.toRegex().matches(email)) { ValidationError.Email }
        email
    }
}

private const val EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
