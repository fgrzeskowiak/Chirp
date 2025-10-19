package com.filippo.auth.presentation.register.success

internal sealed interface RegisterSuccessAction {
    data object OnResendEmailClick: RegisterSuccessAction
}
