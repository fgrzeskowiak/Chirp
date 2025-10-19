package com.filippo.auth.presentation.register.success

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.filippo.auth.presentation.navigation.AuthGraphRoutes
import com.filippo.core.domain.auth.AuthRepository
import com.filippo.core.presentation.util.toUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class RegisterSuccessViewModel(
    private val service: AuthRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val state = MutableStateFlow(
        RegisterSuccessState(
            registeredEmail = savedStateHandle.toRoute<AuthGraphRoutes.RegisterSuccess>().email,
        )
    )
    val uiState = state.asStateFlow()

    private val events = Channel<RegisterSuccessEvent>()
    val uiEvents = events.receiveAsFlow()

    fun onAction(action: RegisterSuccessAction) {
        when (action) {
            is RegisterSuccessAction.OnResendEmailClick -> resendVerification()
        }
    }

    private fun resendVerification() {
        if (state.value.isResendingEmail) return

        state.update { it.copy(isResendingEmail = true) }
        viewModelScope.launch {
            service.resendVerificationEmail(state.value.registeredEmail)
                .fold(
                    ifLeft = { error ->
                        state.update {
                            it.copy(
                                isResendingEmail = false,
                                resendEmailError = error.toUiText(),
                            )
                        }
                    },
                    ifRight = {
                        events.send(RegisterSuccessEvent.ResentEmailSuccessful)
                        state.update { it.copy(isResendingEmail = false) }
                    }
                )
        }
    }

}
