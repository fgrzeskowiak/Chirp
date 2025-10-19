package com.filippo.auth.presentation.email_verification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.close
import chirp.feature.auth.presentation.generated.resources.email_verified_failed
import chirp.feature.auth.presentation.generated.resources.email_verified_failed_desc
import chirp.feature.auth.presentation.generated.resources.email_verified_successfully
import chirp.feature.auth.presentation.generated.resources.email_verified_successfully_desc
import chirp.feature.auth.presentation.generated.resources.login
import chirp.feature.auth.presentation.generated.resources.verifying_account
import com.filippo.core.designsystem.components.brand.ChirpFailureIcon
import com.filippo.core.designsystem.components.brand.ChirpSuccessIcon
import com.filippo.core.designsystem.components.buttons.ChirpButton
import com.filippo.core.designsystem.components.buttons.ChirpButtonStyle
import com.filippo.core.designsystem.components.layouts.ChirpAdaptiveResultLayout
import com.filippo.core.designsystem.components.layouts.ChirpSimpleResultLayout
import com.filippo.core.designsystem.theme.ChirpTheme
import com.filippo.core.designsystem.theme.extended
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun EmailVerificationRoot(
    viewModel: EmailVerificationViewModel = koinViewModel(),
    onLoginClick: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    EmailVerificationScreen(
        state = state,
        onLoginClick = onLoginClick,
    )
}

@Composable
internal fun EmailVerificationScreen(
    state: EmailVerificationState,
    onLoginClick: () -> Unit,
) {
    ChirpAdaptiveResultLayout {
        when {
            state.isVerifying -> VerifyingContent()
            state.isVerified -> ChirpSimpleResultLayout(
                title = stringResource(Res.string.email_verified_successfully),
                description = stringResource(Res.string.email_verified_successfully_desc),
                icon = { ChirpSuccessIcon() },
                primaryButton = {
                    ChirpButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(Res.string.login),
                        onClick = onLoginClick,
                    )
                }
            )

            else -> ChirpSimpleResultLayout(
                title = stringResource(Res.string.email_verified_failed),
                description = stringResource(Res.string.email_verified_failed_desc),
                icon = {
                    Spacer(Modifier.height(42.dp))
                    ChirpFailureIcon(
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(Modifier.height(42.dp))
                },
                primaryButton = {
                    ChirpButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(Res.string.close),
                        style = ChirpButtonStyle.SECONDARY,
                        onClick = onLoginClick,
                    )
                }
            )
        }

    }
}

@Composable
private fun VerifyingContent() {
    Column(
        modifier = Modifier.fillMaxWidth()
            .heightIn(min = 200.dp)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
            color = MaterialTheme.colorScheme.primary,
        )

        Text(
            text = stringResource(Res.string.verifying_account),
            color = MaterialTheme.colorScheme.extended.textSecondary,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Preview
@Composable
private fun SuccessPreview() {
    ChirpTheme {
        EmailVerificationScreen(
            state = EmailVerificationState(isVerified = true),
            onLoginClick = {}
        )
    }
}


@Preview
@Composable
private fun InProgressPreview() {
    ChirpTheme {
        EmailVerificationScreen(
            state = EmailVerificationState(isVerifying = true),
            onLoginClick = {}
        )
    }
}

@Preview
@Composable
private fun FailurePreview() {
    ChirpTheme {
        EmailVerificationScreen(
            state = EmailVerificationState(isVerified = false),
            onLoginClick = {}
        )
    }
}
