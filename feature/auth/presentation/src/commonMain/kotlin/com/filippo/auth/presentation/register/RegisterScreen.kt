package com.filippo.auth.presentation.register

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.email
import chirp.feature.auth.presentation.generated.resources.email_placeholder
import chirp.feature.auth.presentation.generated.resources.login
import chirp.feature.auth.presentation.generated.resources.password
import chirp.feature.auth.presentation.generated.resources.password_hint
import chirp.feature.auth.presentation.generated.resources.register
import chirp.feature.auth.presentation.generated.resources.username
import chirp.feature.auth.presentation.generated.resources.username_hint
import chirp.feature.auth.presentation.generated.resources.username_placeholder
import chirp.feature.auth.presentation.generated.resources.welcome_to_chirp
import com.filippo.core.designsystem.components.buttons.ChirpButton
import com.filippo.core.designsystem.components.buttons.ChirpButtonStyle
import com.filippo.core.designsystem.components.layouts.ChirpAdaptiveFormLayout
import com.filippo.core.designsystem.components.layouts.ChirpScaffold
import com.filippo.core.designsystem.components.textfields.ChirpPasswordTextField
import com.filippo.core.designsystem.components.textfields.ChirpTextField
import com.filippo.core.designsystem.theme.ChirpTheme
import com.filippo.core.presentation.util.ObserveAsEvents
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterRoot(
    viewModel: RegisterViewModel = koinViewModel(),
    onRegisterSuccess: (String) -> Unit,
    onLoginClick: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is RegisterEvent.Success -> onRegisterSuccess(event.email)
        }
    }

    RegisterScreen(
        state = state,
        onInputFocusChanged = { viewModel.onAction(RegisterAction.OnInputFocusChanged) },
        onRegisterClick = { viewModel.onAction(RegisterAction.OnRegisterClick) },
        onLoginClick = onLoginClick,
    )
}

@Composable
internal fun RegisterScreen(
    state: RegisterState,
    onInputFocusChanged: (Boolean) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
) {
    ChirpScaffold {
        ChirpAdaptiveFormLayout(
            headerText = stringResource(Res.string.welcome_to_chirp),
            errorText = state.registrationError?.asString(),
        ) {
            ChirpTextField(
                state = state.username,
                title = stringResource(Res.string.username),
                supportingText = state.usernameError?.asString()
                    ?: stringResource(Res.string.username_hint),
                placeholder = stringResource(Res.string.username_placeholder),
                hasError = state.usernameError != null,
                singleLine = true,
                imeAction = ImeAction.Next,
                onFocusChanged = onInputFocusChanged,
            )
            Spacer(Modifier.height(16.dp))
            ChirpTextField(
                state = state.email,
                title = stringResource(Res.string.email),
                supportingText = state.emailError?.asString(),
                placeholder = stringResource(Res.string.email_placeholder),
                hasError = state.emailError != null,
                singleLine = true,
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email,
                onFocusChanged = onInputFocusChanged,
            )
            Spacer(Modifier.height(16.dp))
            ChirpPasswordTextField(
                state = state.password,
                title = stringResource(Res.string.password),
                supportingText = state.passwordError?.asString()
                    ?: stringResource(Res.string.password_hint),
                hasError = state.passwordError != null,
                onFocusChanged = onInputFocusChanged,
                onKeyboardAction = { onRegisterClick() }
            )

            Spacer(Modifier.height(16.dp))

            ChirpButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.register),
                onClick = onRegisterClick,
                isEnabled = state.canRegister,
                isLoading = state.isRegistering,
            )

            Spacer(Modifier.height(8.dp))

            ChirpButton(
                modifier = Modifier.fillMaxWidth(),
                style = ChirpButtonStyle.SECONDARY,
                text = stringResource(Res.string.login),
                onClick = onLoginClick,
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ChirpTheme {
        RegisterScreen(
            state = RegisterState(),
            onInputFocusChanged = {},
            onRegisterClick = {},
            onLoginClick = {},
        )
    }
}
