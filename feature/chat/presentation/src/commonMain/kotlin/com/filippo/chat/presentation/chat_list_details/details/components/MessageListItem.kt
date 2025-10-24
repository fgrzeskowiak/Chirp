package com.filippo.chat.presentation.chat_list_details.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.filippo.chat.domain.models.MessageDeliveryStatus
import com.filippo.chat.presentation.chat_list_details.model.ChatParticipantUiModel
import com.filippo.chat.presentation.chat_list_details.model.MessageUiModel
import com.filippo.core.designsystem.components.avatar.AvatarUiModel
import com.filippo.core.designsystem.components.brand.ChirpHorizontalDivider
import com.filippo.core.designsystem.theme.ChirpTheme
import com.filippo.core.designsystem.theme.extended
import com.filippo.core.presentation.util.UiText
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MessageListItem(
    message: MessageUiModel,
    modifier: Modifier = Modifier,
    onMessageLongClick: (MessageUiModel.LocalUserMessage) -> Unit,
    onDeleteClick: (MessageUiModel.LocalUserMessage) -> Unit,
    onRetryClick: (MessageUiModel.LocalUserMessage) -> Unit,
) {
    Box(modifier) {
        when (message) {
            is MessageUiModel.DateSeparator -> DateSeparator(date = message.date.asString())
            is MessageUiModel.OtherUserMessage -> OtherUserMessage(message)
            is MessageUiModel.LocalUserMessage -> LocalUserMessage(
                message = message,
                onLongClick = { onMessageLongClick(message) },
                onDeleteClick = { onDeleteClick(message) },
                onRetryClick = { onRetryClick(message) }
            )
        }
    }
}


@Composable
private fun DateSeparator(date: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(40.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ChirpHorizontalDivider(Modifier.weight(1f))
        Text(
            text = date,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.extended.textPlaceholder,
        )
        ChirpHorizontalDivider(Modifier.weight(1f))
    }
}


@Composable
@Preview
private fun MessageListItemLocalMessageUiPreview() {
    ChirpTheme {
        MessageListItem(
            message = MessageUiModel.LocalUserMessage(
                id = "1",
                content = "Hello world, this is a preview message that spans multiple lines",
                deliveryStatus = MessageDeliveryStatus.SENT,
                formattedSentTime = UiText.Dynamic("Friday 2:20pm")
            ),
            onRetryClick = {},
            onMessageLongClick = {},
            onDeleteClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
    }
}

@Composable
@Preview
private fun MessageListItemLocalMessageRetryUiPreview() {
    ChirpTheme {
        MessageListItem(
            message = MessageUiModel.LocalUserMessage(
                id = "1",
                content = "Hello world, this is a preview message that spans multiple lines",
                deliveryStatus = MessageDeliveryStatus.FAILED,
                formattedSentTime = UiText.Dynamic("Friday 2:20pm")
            ),
            onRetryClick = {},
            onMessageLongClick = {},
            onDeleteClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
@Preview
private fun MessageListItemOtherMessageUiPreview() {
    ChirpTheme {
        MessageListItem(
            message = MessageUiModel.OtherUserMessage(
                id = "1",
                content = "Hello world, this is a preview message that spans multiple lines",
                formattedSentTime = UiText.Dynamic("Friday 2:20pm"),
                sender = ChatParticipantUiModel(
                    id = "1",
                    username = "Philipp",
                    avatar = AvatarUiModel(displayText = "PL"),
                )
            ),
            onRetryClick = {},
            onMessageLongClick = {},
            onDeleteClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}