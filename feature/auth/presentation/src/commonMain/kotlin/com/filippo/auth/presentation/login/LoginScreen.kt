package com.filippo.auth.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.create_account
import chirp.feature.auth.presentation.generated.resources.email
import chirp.feature.auth.presentation.generated.resources.email_placeholder
import chirp.feature.auth.presentation.generated.resources.forgot_password
import chirp.feature.auth.presentation.generated.resources.login
import chirp.feature.auth.presentation.generated.resources.password
import chirp.feature.auth.presentation.generated.resources.welcome_back
import com.filippo.core.designsystem.components.brand.ChirpBrandLogo
import com.filippo.core.designsystem.components.buttons.ChirpButton
import com.filippo.core.designsystem.components.buttons.ChirpButtonStyle
import com.filippo.core.designsystem.components.layouts.ChirpAdaptiveFormLayout
import com.filippo.core.designsystem.components.layouts.ChirpScaffold
import com.filippo.core.designsystem.components.textfields.ChirpPasswordTextField
import com.filippo.core.designsystem.components.textfields.ChirpTextField
import com.filippo.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginRoot(
    viewModel: LoginViewModel = koinViewModel(),
    onCreateAccountClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LoginScreen(
        state = state,
        onLoginClick = viewModel::login,
        onForgotPasswordClick = onForgotPasswordClick,
        onCreateAccountClick = onCreateAccountClick,
    )
}

@Composable
private fun LoginScreen(
    state: LoginState,
    onLoginClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onCreateAccountClick: () -> Unit,
) {
    ChirpScaffold {
        ChirpAdaptiveFormLayout(
            modifier = Modifier.fillMaxSize(),
            headerText = stringResource(Res.string.welcome_back),
            errorText = state.error?.asString(),
            logo = { ChirpBrandLogo() },
        ) {
            ChirpTextField(
                modifier = Modifier.fillMaxWidth(),
                state = state.email,
                title = stringResource(Res.string.email),
                placeholder = stringResource(Res.string.email_placeholder),
                keyboardType = KeyboardType.Email,
                singleLine = true,
            )
            Spacer(Modifier.height(16.dp))
            ChirpPasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                state = state.password,
                title = stringResource(Res.string.password),
                keyboardType = KeyboardType.Email,
            )
            Spacer(Modifier.height(16.dp))
            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable(onClick = onForgotPasswordClick),
                text = stringResource(Res.string.forgot_password),
                style = MaterialTheme.typography.titleSmall,
            )
            Spacer(Modifier.height(24.dp))
            ChirpButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.login),
                isEnabled = state.canLogin,
                isLoading = state.isLoggingIn,
                onClick = onLoginClick,
            )
            Spacer(Modifier.height(8.dp))
            ChirpButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.create_account),
                style = ChirpButtonStyle.SECONDARY,
                onClick = onCreateAccountClick,
            )
        }
    }
}

@Preview
@Composable
private fun LightPreview() {
    ChirpTheme {
        LoginScreen(
            state = LoginState(),
            onLoginClick = {},
            onForgotPasswordClick = {},
            onCreateAccountClick = {},
        )
    }
}

@Preview
@Composable
private fun DarkPreview() {
    ChirpTheme(darkTheme = true) {
        LoginScreen(
            state = LoginState(),
            onLoginClick = {},
            onForgotPasswordClick = {},
            onCreateAccountClick = {},
        )
    }
}

