package com.filippo.core.designsystem.components.layouts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.filippo.core.designsystem.components.brand.ChirpBrandLogo
import com.filippo.core.designsystem.theme.ChirpTheme
import com.filippo.core.designsystem.theme.extended
import com.filippo.core.presentation.util.DeviceConfiguration
import com.filippo.core.presentation.util.currentDeviceConfiguration
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpAdaptiveFormLayout(
    headerText: String,
    modifier: Modifier = Modifier,
    errorText: String? = null,
    logo: @Composable () -> Unit = { ChirpBrandLogo() },
    formContent: @Composable ColumnScope.() -> Unit,
) {
    val configuration = currentDeviceConfiguration()
    val headerColor = if (configuration == DeviceConfiguration.MOBILE_LANDSCAPE) {
        MaterialTheme.colorScheme.onBackground
    } else {
        MaterialTheme.colorScheme.extended.textPrimary
    }

    when (configuration) {
        DeviceConfiguration.MOBILE_PORTRAIT -> {
            ChirpSurface(
                modifier = modifier
                    .consumeWindowInsets(WindowInsets.navigationBars)
                    .consumeWindowInsets(WindowInsets.displayCutout),
                header = {
                    Spacer(Modifier.height(32.dp))
                    logo()
                    Spacer(Modifier.height(32.dp))
                }
            ) {
                Spacer(Modifier.height(24.dp))
                AuthHeaderSection(
                    headerText = headerText,
                    headerColor = headerColor,
                    errorText = errorText,
                )
                Spacer(Modifier.height(24.dp))
                formContent()
            }
        }

        DeviceConfiguration.MOBILE_LANDSCAPE -> {
            Row(
                modifier = modifier
                    .fillMaxSize()
                    .consumeWindowInsets(WindowInsets.displayCutout)
                    .consumeWindowInsets(WindowInsets.navigationBars),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Spacer(Modifier.height(16.dp))
                    logo()
                    AuthHeaderSection(
                        headerText = headerText,
                        headerColor = headerColor,
                        textAlign = TextAlign.Start,
                        errorText = errorText,
                    )
                }

                ChirpSurface(modifier = Modifier.weight(1f)) {
                    Spacer(Modifier.height(16.dp))
                    formContent()
                    Spacer(Modifier.height(16.dp))
                }
            }
        }

        DeviceConfiguration.TABLET_PORTRAIT,
        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP -> {
            Column(
                modifier = modifier.fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                logo()
                Column(
                    modifier = Modifier
                        .widthIn(max = 480.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(32.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 24.dp, vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    AuthHeaderSection(
                        headerText = headerText,
                        headerColor = headerColor,
                        errorText = errorText,
                    )
                    formContent()
                }
            }
        }
    }
}

@Composable
private fun ColumnScope.AuthHeaderSection(
    headerText: String,
    headerColor: Color,
    textAlign: TextAlign = TextAlign.Center,
    errorText: String? = null,
) {
    Text(
        text = headerText,
        style = MaterialTheme.typography.titleLarge,
        color = headerColor,
        textAlign = textAlign,
        modifier = Modifier.fillMaxWidth(),
    )

    AnimatedVisibility(
        modifier = Modifier.align(Alignment.CenterHorizontally),
        visible = errorText != null
    ) {
        if (errorText != null) {
            Text(
                text = errorText,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error,
                textAlign = textAlign,
            )
        }
    }
}

@Composable
@Preview
private fun ChirpAdaptiveFormLayoutLightPreview() {
    ChirpTheme {
        ChirpAdaptiveFormLayout(
            headerText = "Welcome to Chirp",
            errorText = "Login failed!",
            logo = { ChirpBrandLogo() },
            formContent = {
                Text(
                    text = "Login with your email and password",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            },

            )
    }
}

@Composable
@Preview
private fun ChirpAdaptiveFormLayoutDarkPreview() {
    ChirpTheme(darkTheme = true) {
        ChirpAdaptiveFormLayout(
            headerText = "Welcome to Chirp",
            errorText = "Login failed!",
            logo = { ChirpBrandLogo() },
            formContent = {
                Text(
                    text = "Login with your email and password",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            },
        )
    }
}
