package com.filippo.chat.presentation.chat_list_details.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.filippo.chat.presentation.chat_list_details.model.MessageUiModel
import com.filippo.core.designsystem.components.avatar.ChirpAvatarPhoto
import com.filippo.core.designsystem.components.chat.ChatBubbleShape.TrianglePosition
import com.filippo.core.designsystem.components.chat.ChirpChatBubble

@Composable
fun OtherUserMessage(
    message: MessageUiModel.OtherUserMessage,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ChirpAvatarPhoto(message.sender.avatar)
        ChirpChatBubble(
            message = message.content,
            sender = message.sender.username,
            color = message.background,
            trianglePosition = TrianglePosition.LEFT,
            timestamp = message.formattedSentTime.asString(),
        )
    }
}