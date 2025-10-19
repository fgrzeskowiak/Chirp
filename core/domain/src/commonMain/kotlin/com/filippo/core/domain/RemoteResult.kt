package com.filippo.core.domain

import arrow.core.Either

typealias RemoteResult<T> = Either<DataError.Remote, T>
