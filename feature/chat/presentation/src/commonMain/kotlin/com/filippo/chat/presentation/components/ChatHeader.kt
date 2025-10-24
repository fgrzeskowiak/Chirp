package com.filippo.chat.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.filippo.core.presentation.util.drawDivider

@Composable
fun ChatHeader(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 80.dp)
            .drawDivider(color = MaterialTheme.colorScheme.outline)
            .padding(
                vertical = 20.dp,
                horizontal = 16.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}