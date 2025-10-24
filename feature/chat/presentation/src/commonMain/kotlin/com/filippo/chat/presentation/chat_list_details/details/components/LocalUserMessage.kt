package com.filippo.chat.presentation.chat_list_details.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.delete_for_everyone
import chirp.feature.chat.presentation.generated.resources.reload_icon
import chirp.feature.chat.presentation.generated.resources.retry
import chirp.feature.chat.presentation.generated.resources.you
import com.filippo.chat.domain.models.MessageDeliveryStatus
import com.filippo.chat.presentation.chat_list_details.model.MessageUiModel
import com.filippo.core.designsystem.components.chat.ChatBubbleShape.TrianglePosition
import com.filippo.core.designsystem.components.chat.ChirpChatBubble
import com.filippo.core.designsystem.components.dropdown.ChirpDropdownMenu
import com.filippo.core.designsystem.components.dropdown.DropdownMenuItem
import com.filippo.core.designsystem.theme.extended
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun LocalUserMessage(
    message: MessageUiModel.LocalUserMessage,
    onLongClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isMenuOpen by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
    ) {
        Box(Modifier.weight(1f)) {
            ChirpChatBubble(
                message = message.content,
                sender = stringResource(Res.string.you),
                trianglePosition = TrianglePosition.RIGHT,
                timestamp = message.formattedSentTime.asString(),
                status = { MessageStatus(message.deliveryStatus) },
                onLongClick = onLongClick,
            )

            ChirpDropdownMenu(
                isOpen = isMenuOpen,
                items = listOf(
                    DropdownMenuItem(
                        icon = Icons.Default.Delete,
                        text = stringResource(Res.string.delete_for_everyone),
                        contentColor = MaterialTheme.colorScheme.extended.destructiveHover,
                        onClick = onDeleteClick
                    )
                ),
                onDismiss = { isMenuOpen = false }
            )
        }

        if (message.deliveryStatus == MessageDeliveryStatus.FAILED) {
            IconButton(onClick = onRetryClick) {
                Icon(
                    imageVector = vectorResource(Res.drawable.reload_icon),
                    contentDescription = stringResource(Res.string.retry),
                    tint = MaterialTheme.colorScheme.error,
                )
            }
        }
    }
}