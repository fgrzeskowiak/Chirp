package com.filippo.core.designsystem.components.brand

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.filippo.core.designsystem.theme.extended

@Composable
fun ChirpFailureIcon(
    modifier: Modifier = Modifier,
) {
    Icon(
        modifier = modifier,
        contentDescription = null,
        imageVector = Icons.Default.Close,
        tint = MaterialTheme.colorScheme.error,
    )
}
