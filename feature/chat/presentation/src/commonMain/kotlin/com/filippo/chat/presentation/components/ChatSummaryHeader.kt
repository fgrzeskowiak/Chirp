package com.filippo.chat.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.filippo.chat.presentation.chat_list_details.model.ChatUiModel
import com.filippo.core.designsystem.components.avatar.AvatarUiModel
import com.filippo.core.designsystem.components.avatar.ChirpStackedAvatars
import com.filippo.core.designsystem.theme.ChirpTheme
import com.filippo.core.designsystem.theme.extended
import com.filippo.core.designsystem.theme.titleXSmall
import com.filippo.core.presentation.util.UiText
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatSummary(
    chat: ChatUiModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ChirpStackedAvatars(avatars = chat.avatars, remaining = chat.remainingAvatars)
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = chat.title.asString(),
                style = MaterialTheme.typography.titleXSmall,
                color = MaterialTheme.colorScheme.extended.textPrimary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )
            chat.subtitle?.let {
                Text(
                    text = it.asString(),
                    color = MaterialTheme.colorScheme.extended.textPlaceholder,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ChatSummaryHeaderPreview() {
    ChirpTheme {
        ChatSummary(
            chat = ChatUiModel(
                id = "1",
                title = UiText.Dynamic("Group Chat"),
                subtitle = UiText.Dynamic("Bolek i Lolek"),
                avatars = listOf(AvatarUiModel("BO"), AvatarUiModel("LO")),
                remainingAvatars = 2,
                lastMessage = AnnotatedString("Last message"),
            )
        )
    }
}