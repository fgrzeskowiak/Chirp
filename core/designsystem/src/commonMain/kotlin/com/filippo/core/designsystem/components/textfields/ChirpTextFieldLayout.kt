package com.filippo.core.designsystem.components.textfields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.filippo.core.designsystem.theme.extended

@Composable
fun ChirpTextFieldLayout(
    modifier: Modifier = Modifier,
    title: String? = null,
    supportingText: String? = null,
    hasError: Boolean = false,
    textField: @Composable () -> Unit,
) {
    Column(modifier = modifier) {
        if (title != null) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.extended.textSecondary,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        textField()

        if (supportingText != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = supportingText,
                style = MaterialTheme.typography.bodySmall,
                color = if (hasError) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.extended.textSecondary
                },
            )
        }
    }

}
