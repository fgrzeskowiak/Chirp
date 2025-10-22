package com.filippo.auth.presentation.forgot_password

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.email
import chirp.feature.auth.presentation.generated.resources.email_placeholder
import chirp.feature.auth.presentation.generated.resources.forgot_password
import chirp.feature.auth.presentation.generated.resources.forgot_password_email_sent_successfully
import chirp.feature.auth.presentation.generated.resources.submit
import com.filippo.core.designsystem.components.brand.ChirpBrandLogo
import com.filippo.core.designsystem.components.buttons.ChirpButton
import com.filippo.core.designsystem.components.layouts.ChirpAdaptiveFormLayout
import com.filippo.core.designsystem.components.layouts.ChirpScaffold
import com.filippo.core.designsystem.components.textfields.ChirpTextField
import com.filippo.core.designsystem.theme.ChirpTheme
import com.filippo.core.designsystem.theme.extended
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ForgotPasswordRoot(
    viewModel: ForgotPasswordViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ForgotPasswordScreen(
        state = state,
        onSubmitClick = viewModel::onSubmit
    )
}

@Composable
internal fun ForgotPasswordScreen(
    state: ForgotPasswordState,
    onSubmitClick: () -> Unit,
) {
    ChirpScaffold {
        ChirpAdaptiveFormLayout(
            headerText = stringResource(Res.string.forgot_password),
            errorText = state.requestError?.asString(),
            logo = { ChirpBrandLogo() },
        ) {
            ChirpTextField(
                modifier = Modifier.fillMaxWidth(),
                state = state.email,
                placeholder = stringResource(Res.string.email_placeholder),
                title = stringResource(Res.string.email),
                hasError = state.validationError != null,
                supportingText = state.validationError?.asString(),
                keyboardType = KeyboardType.Email,
                singleLine = true,
            )
            Spacer(Modifier.height(16.dp))
            ChirpButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.submit),
                onClick = onSubmitClick,
                isEnabled = state.canSubmit,
            )
            Spacer(Modifier.height(16.dp))
            if (state.isSuccess) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(Res.string.forgot_password_email_sent_successfully),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.extended.success,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ChirpTheme {
        ForgotPasswordScreen(
            state = ForgotPasswordState(isSuccess = true),
            onSubmitClick = {},
        )
    }
}
