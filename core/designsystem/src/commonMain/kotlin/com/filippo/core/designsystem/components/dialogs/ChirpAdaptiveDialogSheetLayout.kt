package com.filippo.core.designsystem.components.dialogs

import androidx.compose.runtime.Composable
import com.filippo.core.presentation.util.currentDeviceConfiguration

@Composable
fun ChirpAdaptiveDialogSheetLayout(
    onDismiss: () -> Unit,
    content: @Composable () -> Unit,
) {
    val configuration = currentDeviceConfiguration()

    if (configuration.isMobile) {
        ChirpBottomSheet(onDismiss = onDismiss, content = content)
    } else {
        ChirpDialog(onDismiss, content = content)
    }
}