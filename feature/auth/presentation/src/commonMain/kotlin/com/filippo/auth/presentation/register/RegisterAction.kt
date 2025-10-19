package com.filippo.auth.presentation.register 

sealed interface RegisterAction {
    data object OnRegisterClick: RegisterAction
    data object OnInputFocusChanged: RegisterAction
}
