package com.filippo.chat.presentation.chat_list_details.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.filippo.core.designsystem.theme.ChirpTheme
import com.filippo.core.designsystem.theme.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatDateChip(
    date: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = date,
        modifier = modifier
            .clip(RoundedCornerShape(100))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(100)
            )
            .padding(
                vertical = 4.dp,
                horizontal = 12.dp
            ),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.extended.textPlaceholder
    )
}

@Composable
@Preview(showBackground = true)
private fun ChatDateChipPreview() {
    ChirpTheme {
        ChatDateChip("June 15")
    }
}