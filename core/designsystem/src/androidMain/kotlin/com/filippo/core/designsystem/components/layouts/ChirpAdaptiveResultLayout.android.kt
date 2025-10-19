package com.filippo.core.designsystem.components.layouts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.filippo.core.designsystem.theme.ChirpTheme

@Composable
@PreviewLightDark
@PreviewScreenSizes
private fun ChirpAdaptiveResultLayoutPreview() {
    ChirpTheme {
        ChirpAdaptiveResultLayout(
            modifier = Modifier.fillMaxSize(),
            content = {
                Text(
                    text = "Login successful!",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        )
    }
}
