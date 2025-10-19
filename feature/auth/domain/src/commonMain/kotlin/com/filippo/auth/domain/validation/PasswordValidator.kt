package com.filippo.auth.domain.validation

import arrow.core.raise.either
import arrow.core.raise.ensure

object PasswordValidator {
    private const val MIN_LENGTH = 9

    fun validate(password: String): ValidationResult = either {
        ensure(password.length >= MIN_LENGTH) { ValidationError.Password }
        ensure(password.any { it.isDigit() }) { ValidationError.Password }
        ensure(password.any { it.isUpperCase() }) { ValidationError.Password }
        password
    }
}
