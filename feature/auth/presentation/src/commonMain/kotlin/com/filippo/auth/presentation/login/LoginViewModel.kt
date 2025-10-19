package com.filippo.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.error_email_not_verified
import chirp.feature.auth.presentation.generated.resources.error_invalid_credentials
import com.filippo.auth.domain.login.LoginUseCase
import com.filippo.core.domain.DataError
import com.filippo.core.presentation.util.UiText
import com.filippo.core.presentation.util.toUiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LoginViewModel(
    private val login: LoginUseCase,
) : ViewModel() {

    private val state = MutableStateFlow(LoginState())
    val uiState = state.asStateFlow()

    fun login() {
        if (!state.value.canLogin) return
        state.update { it.copy(isLoggingIn = true, canLogin = false) }
        viewModelScope.launch {
            login(
                email = state.value.email.text.toString(),
                password = state.value.password.text.toString(),
            ).fold(
                ifLeft = { error ->
                    state.update {
                        it.copy(
                            error = when (error) {
                                DataError.Remote.UNAUTHORIZED -> UiText.Resource(Res.string.error_invalid_credentials)
                                DataError.Remote.FORBIDDEN -> UiText.Resource(Res.string.error_email_not_verified)
                                else -> error.toUiText()
                            },
                            isLoggingIn = false,
                            canLogin = true,
                        )
                    }
                },
                ifRight = { state.update { it.copy(isLoggingIn = false, canLogin = false) } }
            )
        }
    }
}
