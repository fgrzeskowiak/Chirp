package com.filippo.chat.presentation.chat_list_details.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.filippo.chat.presentation.chat_list_details.model.ChatUiModel
import com.filippo.chat.presentation.components.ChatSummary
import com.filippo.core.designsystem.components.avatar.AvatarUiModel
import com.filippo.core.designsystem.theme.ChirpTheme
import com.filippo.core.designsystem.theme.extended
import com.filippo.core.presentation.util.UiText
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatListItem(
    chat: ChatUiModel,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(
                color = if (isSelected) {
                    MaterialTheme.colorScheme.surface
                } else {
                    MaterialTheme.colorScheme.extended.surfaceLower
                }
            )
            .then(
                if (isSelected) {
                    Modifier.drawSelectionBar()
                } else {
                    Modifier
                }
            )
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ChatSummary(chat)
        chat.lastMessage?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.extended.textSecondary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun Modifier.drawSelectionBar(): Modifier {
    val color = MaterialTheme.colorScheme.primary
    return drawBehind {
        val width = 4.dp.toPx()
        drawLine(
            color = color,
            start = Offset(x = size.width - width / 2, y = 0f),
            end = Offset(x = size.width - width / 2, y = size.height),
            strokeWidth = width,
        )
    }
}

@Composable
@Preview
private fun ChatListItemUiPreview() {
    ChirpTheme(darkTheme = true) {
        ChatListItem(
            isSelected = true,
            modifier = Modifier.fillMaxWidth(),
            chat = ChatUiModel(
                id = "1",
                avatars = listOf(
                    AvatarUiModel(displayText = "CI"),
                    AvatarUiModel(displayText = "JO"),
                ),
                title = UiText.Dynamic("Group Chat"),
                subtitle = UiText.Dynamic("You, Cinderella, Josh"),
                lastMessage = AnnotatedString(
                    "Phillip: This is a last chat message that was sent by Philipp " +
                            "and goes over multiple lines to showcase the ellipsis",
                ),
            )
        )
    }
}