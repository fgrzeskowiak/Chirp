package com.filippo.chat.presentation.chat_list_details.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.no_messages
import chirp.feature.chat.presentation.generated.resources.no_messages_subtitle
import com.filippo.chat.presentation.chat_list_details.model.MessageUiModel
import com.filippo.chat.presentation.components.EmptyChatSection
import org.jetbrains.compose.resources.stringResource

@Composable
fun MessageList(
    messages: List<MessageUiModel>,
    listState: LazyListState,
    onMessageLongClick: (MessageUiModel.LocalUserMessage) -> Unit,
    onRetryMessageClick: (MessageUiModel.LocalUserMessage) -> Unit,
    onDeleteMessageClick: (MessageUiModel.LocalUserMessage) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (messages.isEmpty()) {
        Box(
            modifier = modifier.fillMaxWidth().padding(vertical = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            EmptyChatSection(
                title = stringResource(Res.string.no_messages),
                description = stringResource(Res.string.no_messages_subtitle),
            )
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            state = listState,
            contentPadding = PaddingValues(16.dp),
            reverseLayout = true,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = messages,
                key = MessageUiModel::id
            ) { message ->
                MessageListItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem(),
                    message = message,
                    onMessageLongClick = onMessageLongClick,
                    onDeleteClick = onDeleteMessageClick,
                    onRetryClick = onRetryMessageClick
                )
            }
        }
    }
}