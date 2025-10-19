package com.filippo.core.presentation.util

import chirp.core.presentation.generated.resources.Res
import chirp.core.presentation.generated.resources.error_bad_request
import chirp.core.presentation.generated.resources.error_conflict
import chirp.core.presentation.generated.resources.error_disk_full
import chirp.core.presentation.generated.resources.error_forbidden
import chirp.core.presentation.generated.resources.error_no_internet
import chirp.core.presentation.generated.resources.error_not_found
import chirp.core.presentation.generated.resources.error_payload_too_large
import chirp.core.presentation.generated.resources.error_request_timeout
import chirp.core.presentation.generated.resources.error_serialization
import chirp.core.presentation.generated.resources.error_server
import chirp.core.presentation.generated.resources.error_service_unavailable
import chirp.core.presentation.generated.resources.error_too_many_requests
import chirp.core.presentation.generated.resources.error_unauthorized
import chirp.core.presentation.generated.resources.error_unknown
import com.filippo.core.domain.DataError
import com.filippo.core.domain.DataError.Local
import com.filippo.core.domain.DataError.Remote

fun DataError.toUiText(): UiText = UiText.Resource(
    when (this) {
        Local.DISK_FULL -> Res.string.error_disk_full
        Local.NOT_FOUND -> Res.string.error_not_found
        Local.UNKNOWN -> Res.string.error_unknown
        Remote.BAD_REQUEST -> Res.string.error_bad_request
        Remote.REQUEST_TIMEOUT -> Res.string.error_request_timeout
        Remote.UNAUTHORIZED -> Res.string.error_unauthorized
        Remote.FORBIDDEN -> Res.string.error_forbidden
        Remote.NOT_FOUND -> Res.string.error_not_found
        Remote.CONFLICT -> Res.string.error_conflict
        Remote.TOO_MANY_REQUESTS -> Res.string.error_too_many_requests
        Remote.NO_INTERNET -> Res.string.error_no_internet
        Remote.PAYLOAD_TOO_LARGE -> Res.string.error_payload_too_large
        Remote.SERVER_ERROR -> Res.string.error_server
        Remote.SERVICE_UNAVAILABLE -> Res.string.error_service_unavailable
        Remote.SERIALIZATION -> Res.string.error_serialization
        Remote.UNKNOWN -> Res.string.error_unknown
    }
)

