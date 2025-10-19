package com.filippo.auth.presentation.email_verification

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.filippo.auth.presentation.navigation.AuthGraphRoutes
import com.filippo.core.domain.auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class EmailVerificationViewModel(
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val token = savedStateHandle.toRoute<AuthGraphRoutes.EmailVerification>().token
    private val state = MutableStateFlow(EmailVerificationState())
    val uiState = state.asStateFlow()

    init {
        verifyEmail()
    }

    private fun verifyEmail() {
        if (token == null) {
            state.update { it.copy(isVerified = false) }
            return
        }
        viewModelScope.launch {
            state.update { it.copy(isVerifying = true) }

            authRepository.verifyEmail(token)
                .fold(
                    ifLeft = {
                        state.update { it.copy(isVerifying = false, isVerified = false) }
                    },
                    ifRight = {
                        state.update { it.copy(isVerifying = false, isVerified = true) }
                    }
                )
        }
    }

}
