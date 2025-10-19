package com.filippo.auth.presentation.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.error_invalid_email
import com.filippo.auth.domain.AuthError
import com.filippo.auth.domain.forgot_password.ForgotPasswordUseCase
import com.filippo.auth.domain.validation.ValidationError
import com.filippo.auth.presentation.mapper.toUiText
import com.filippo.core.presentation.util.UiText
import com.filippo.core.presentation.util.toUiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class ForgotPasswordViewModel(
    private val forgotPassword: ForgotPasswordUseCase,
) : ViewModel() {

    private val state = MutableStateFlow(ForgotPasswordState())
    val uiState = state.asStateFlow()

    fun onSubmit() {
        if (!state.value.canSubmit) return
        state.update {
            it.copy(
                isLoading = true,
                canSubmit = false,
                validationError = null,
                requestError = null,
            )
        }
        forgotPassword()
    }

    private fun forgotPassword() {
        viewModelScope.launch {
            forgotPassword(state.value.email.text.toString())
                .fold(
                    ifLeft = { error ->
                        val validationErrors = (error as? AuthError.Validation)
                            ?.validationErrors
                            .orEmpty()

                        state.update { state ->
                            state.copy(
                                isLoading = false,
                                canSubmit = true,
                                requestError = (error as? AuthError.Request)?.error?.toUiText(),
                                validationError = validationErrors.find { it == ValidationError.Email }?.toUiText()
                            )
                        }
                    },
                    ifRight = {
                        state.update { state ->
                            state.copy(
                                isLoading = false,
                                canSubmit = true,
                                isSuccess = true,
                            )
                        }
                    }
                )
        }
    }
}
