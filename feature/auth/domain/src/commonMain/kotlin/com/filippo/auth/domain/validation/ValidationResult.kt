package com.filippo.auth.domain.validation

import arrow.core.Either

typealias ValidationResult = Either<ValidationError, String>
