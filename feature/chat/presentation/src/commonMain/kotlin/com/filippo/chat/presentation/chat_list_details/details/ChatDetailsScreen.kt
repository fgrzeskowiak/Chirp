package com.filippo.chat.presentation.chat_list_details.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.filippo.chat.domain.models.MessageDeliveryStatus
import com.filippo.chat.presentation.chat_list_details.details.components.ChatDetailHeader
import com.filippo.chat.presentation.chat_list_details.details.components.MessageBox
import com.filippo.chat.presentation.chat_list_details.details.components.MessageList
import com.filippo.chat.presentation.chat_list_details.model.ChatParticipantUiModel
import com.filippo.chat.presentation.chat_list_details.model.ChatUiModel
import com.filippo.chat.presentation.chat_list_details.model.MessageUiModel
import com.filippo.chat.presentation.components.ChatHeader
import com.filippo.core.designsystem.components.avatar.AvatarUiModel
import com.filippo.core.designsystem.theme.ChirpTheme
import com.filippo.core.designsystem.theme.extended
import com.filippo.core.presentation.util.UiText
import com.filippo.core.presentation.util.clearFocusOnTap
import com.filippo.core.presentation.util.currentDeviceConfiguration
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatDetailsScreenRoot(
    chatId: String?,
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    viewModel: ChatDetailsViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(chatId) {
        viewModel.onChatSelected(chatId)
    }

    ChatDetailsScreen(
        state = state,
        showBackButton = showBackButton,
        onBackClick = onBackClick,
        onAction = viewModel::onAction,
    )
}

@Composable
fun ChatDetailsScreen(
    state: ChatDetailsState,
    showBackButton: Boolean,
    onAction: (ChatDetailsAction) -> Unit,
    onBackClick: () -> Unit,
) {
    val configuration = currentDeviceConfiguration()
    val listState = rememberLazyListState()
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        contentWindowInsets = WindowInsets.safeDrawing,
        containerColor = if (!configuration.isWideScreen) {
            MaterialTheme.colorScheme.surface
        } else {
            MaterialTheme.colorScheme.extended.surfaceLower
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .clearFocusOnTap()
                .padding(innerPadding)
                .then(
                    if (configuration.isWideScreen) {
                        Modifier.padding(horizontal = 8.dp)
                    } else {
                        Modifier
                    }
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                RoundedCornerColumn(showRoundedCorners = configuration.isWideScreen) {
                    ChatHeader {
                        ChatDetailHeader(
                            modifier = Modifier.fillMaxWidth(),
                            chat = state.chat,
                            showBackButton = showBackButton,
                            onChatOptionsClick = { onAction(ChatDetailsAction.OnChatOptionsClick) },
                            onManageChatClick = { onAction(ChatDetailsAction.OnChatMembersClick) },
                            onLeaveChatClick = { },
                            onBackClick = onBackClick
                        )
                    }

                    MessageList(
                        modifier = Modifier.weight(1f),
                        messages = state.messages,
                        listState = listState,
                        onMessageLongClick = { onAction(ChatDetailsAction.OnMessageLongClick(it)) },
                        onRetryMessageClick = { onAction(ChatDetailsAction.OnRetryMessageClick(it)) },
                        onDeleteMessageClick = { onAction(ChatDetailsAction.OnDeleteMessageClick(it)) },
                    )

                    AnimatedVisibility(
                        visible = !configuration.isWideScreen && state.chat != null
                    ) {
                        MessageBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 16.dp),
                            state = state.messageInput,
                            isInputEnabled = state.canSend,
                            connectionError = state.connectionError,
                            onSendClick = { onAction(ChatDetailsAction.OnSendMessageClick) }
                        )
                    }
                }

                if (configuration.isWideScreen) {
                    Spacer(Modifier.height(8.dp))
                }

                AnimatedVisibility(
                    visible = configuration.isWideScreen && state.chat != null
                ) {
                    RoundedCornerColumn(showRoundedCorners = true) {
                        MessageBox(
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            state = state.messageInput,
                            isInputEnabled = state.canSend,
                            connectionError = state.connectionError,
                            onSendClick = { onAction(ChatDetailsAction.OnSendMessageClick) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RoundedCornerColumn(
    showRoundedCorners: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable (ColumnScope) -> Unit,
) {
    Column(
        modifier = modifier
            .shadow(
                elevation = if (showRoundedCorners) 8.dp else 0.dp,
                shape = if (showRoundedCorners) RoundedCornerShape(24.dp) else RectangleShape,
                spotColor = Color.Black.copy(alpha = .2f)
            )
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = if (showRoundedCorners) RoundedCornerShape(24.dp) else RectangleShape,
            ),
        content = content,
    )
}

@Preview
@Composable
private fun ChatDetailEmptyPreview() {
    ChirpTheme {
        ChatDetailsScreen(
            state = ChatDetailsState(),
            showBackButton = true,
            onAction = {},
            onBackClick = {},
        )
    }
}

@Preview
@Composable
private fun ChatDetailMessagesPreview() {
    ChirpTheme(darkTheme = true) {
        ChatDetailsScreen(
            state = ChatDetailsState(
                messageInput = rememberTextFieldState("This is a new message!"),
                canSend = true,
                chat = ChatUiModel(
                    id = "1",
                    title = UiText.Dynamic("Group Chat"),
                    subtitle = UiText.Dynamic("You, Bolek, Lolek"),
                    avatars = listOf(AvatarUiModel("BO"), AvatarUiModel("LO")),
                    lastMessage = AnnotatedString(
                        "This is a last chat message that was sent by Philipp " +
                                "and goes over multiple lines to showcase the ellipsis"
                    ),
                ),
                messages = List(20) {
                    if (it % 2 == 0) {
                        MessageUiModel.LocalUserMessage(
                            id = it.toString(),
                            content = "Hello world!",
                            deliveryStatus = MessageDeliveryStatus.SENT,
                            formattedSentTime = UiText.Dynamic("Friday, Aug 20")
                        )
                    } else {
                        MessageUiModel.OtherUserMessage(
                            id = it.toString(),
                            content = "Hello world!",
                            sender = ChatParticipantUiModel(
                                id = "1",
                                username = "Bolek",
                                avatar = AvatarUiModel("BO")
                            ),
                            formattedSentTime = UiText.Dynamic("Friday, Aug 20"),
                        )
                    }
                }
            ),
            showBackButton = false,
            onAction = {},
            onBackClick = {},
        )
    }
}