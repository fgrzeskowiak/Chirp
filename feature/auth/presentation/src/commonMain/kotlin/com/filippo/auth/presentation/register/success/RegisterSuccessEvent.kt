package com.filippo.auth.presentation.register.success

internal sealed interface RegisterSuccessEvent {
    data object ResentEmailSuccessful: RegisterSuccessEvent
}
