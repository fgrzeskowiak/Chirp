package com.filippo.auth.presentation.register.success

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.account_successfully_created
import chirp.feature.auth.presentation.generated.resources.login
import chirp.feature.auth.presentation.generated.resources.resend_verification_email
import chirp.feature.auth.presentation.generated.resources.resend_verification_email_success
import chirp.feature.auth.presentation.generated.resources.verification_email_sent_to_x
import com.filippo.core.designsystem.components.brand.ChirpSuccessIcon
import com.filippo.core.designsystem.components.buttons.ChirpButton
import com.filippo.core.designsystem.components.buttons.ChirpButtonStyle
import com.filippo.core.designsystem.components.layouts.ChirpAdaptiveResultLayout
import com.filippo.core.designsystem.components.layouts.ChirpSimpleResultLayout
import com.filippo.core.designsystem.components.layouts.ChirpScaffold
import com.filippo.core.designsystem.theme.ChirpTheme
import com.filippo.core.presentation.util.ObserveAsEvents
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun RegisterSuccessRoot(
    viewModel: RegisterSuccessViewModel = koinViewModel(),
    onLoginClick: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarState = remember { SnackbarHostState() }

    RegisterSuccessScreen(
        state = state,
        snackbarState = snackbarState,
        onLoginClick = onLoginClick,
        onResendEmailClick = { viewModel.onAction(RegisterSuccessAction.OnResendEmailClick) },
    )

    ObserveAsEvents(viewModel.uiEvents) { event ->
        when (event) {
            is RegisterSuccessEvent.ResentEmailSuccessful -> snackbarState.showSnackbar(
                getString(Res.string.resend_verification_email_success)
            )
        }
    }
}

@Composable
internal fun RegisterSuccessScreen(
    state: RegisterSuccessState,
    snackbarState: SnackbarHostState,
    onLoginClick: () -> Unit,
    onResendEmailClick: () -> Unit,
) {
    ChirpScaffold(
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier.padding(bottom = 24.dp),
                hostState = snackbarState
            )
        }
    ) {
        ChirpAdaptiveResultLayout {
            ChirpSimpleResultLayout(
                title = stringResource(Res.string.account_successfully_created),
                description = stringResource(
                    Res.string.verification_email_sent_to_x,
                    state.registeredEmail
                ),
                secondaryError = state.resendEmailError?.asString(),
                icon = { ChirpSuccessIcon() },
                primaryButton = {
                    ChirpButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(Res.string.login),
                        onClick = onLoginClick,
                    )
                },
                secondaryButton = {
                    ChirpButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(Res.string.resend_verification_email),
                        isEnabled = !state.isResendingEmail,
                        isLoading = state.isResendingEmail,
                        style = ChirpButtonStyle.SECONDARY,
                        onClick = onResendEmailClick,
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ChirpTheme {
        RegisterSuccessScreen(
            state = RegisterSuccessState(
                registeredEmail = "test@test.com"
            ),
            snackbarState = remember { SnackbarHostState() },
            onLoginClick = {},
            onResendEmailClick = {},
        )
    }
}
