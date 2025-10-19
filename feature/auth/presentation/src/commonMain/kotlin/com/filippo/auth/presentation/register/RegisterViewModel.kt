package com.filippo.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.error_invalid_email
import chirp.feature.auth.presentation.generated.resources.error_invalid_password
import chirp.feature.auth.presentation.generated.resources.error_invalid_username
import com.filippo.auth.domain.AuthError
import com.filippo.auth.domain.register.RegisterUseCase
import com.filippo.auth.domain.validation.ValidationError
import com.filippo.auth.presentation.mapper.toUiText
import com.filippo.core.presentation.util.UiText
import com.filippo.core.presentation.util.toUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class RegisterViewModel(
    private val register: RegisterUseCase,
) : ViewModel() {

    private val state = MutableStateFlow(RegisterState())
    val uiState: StateFlow<RegisterState> = state.asStateFlow()

    private val eventChannel = Channel<RegisterEvent>()
    val events: Flow<RegisterEvent> = eventChannel.receiveAsFlow()

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnInputFocusChanged -> clearInputErrors()
            RegisterAction.OnRegisterClick -> register()
        }
    }

    private fun clearInputErrors() {
        state.update {
            it.copy(
                emailError = null,
                usernameError = null,
                passwordError = null,
                registrationError = null,
                canRegister = true,
            )
        }
    }

    private fun register() {
        state.update { it.copy(isRegistering = true, canRegister = false) }
        viewModelScope.launch {
            with(state.value) {
                register(
                    email = email.text.toString(),
                    password = password.text.toString(),
                    username = username.text.toString(),
                ).fold(
                    ifLeft = ::setErrors,
                    ifRight = {
                        state.update { it.copy(isRegistering = false) }
                        eventChannel.send(RegisterEvent.Success(email.text.toString()))
                    }
                )
            }
        }
    }

    private fun setErrors(error: AuthError) {
        val validationErrors = (error as? AuthError.Validation)
            ?.validationErrors
            .orEmpty()

        state.update { state ->
            state.copy(
                emailError = validationErrors.find { it == ValidationError.Email }?.toUiText(),
                usernameError = validationErrors.find { it == ValidationError.Username }?.toUiText(),
                passwordError = validationErrors.find { it == ValidationError.Password }?.toUiText(),
                registrationError = (error as? AuthError.Request)?.error?.toUiText(),
                canRegister = false,
                isRegistering = false,
            )
        }
    }
}
