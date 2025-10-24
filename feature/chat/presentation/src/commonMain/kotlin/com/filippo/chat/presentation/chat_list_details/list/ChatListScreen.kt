package com.filippo.chat.presentation.chat_list_details.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.cancel
import chirp.feature.chat.presentation.generated.resources.create_chat
import chirp.feature.chat.presentation.generated.resources.do_you_want_to_logout
import chirp.feature.chat.presentation.generated.resources.do_you_want_to_logout_desc
import chirp.feature.chat.presentation.generated.resources.logout
import chirp.feature.chat.presentation.generated.resources.no_messages
import chirp.feature.chat.presentation.generated.resources.no_messages_subtitle
import com.filippo.chat.presentation.chat_list_details.list.components.ChatListHeader
import com.filippo.chat.presentation.components.EmptyChatSection
import com.filippo.core.designsystem.components.buttons.ChirpFloatingActionButton
import com.filippo.core.designsystem.components.dialogs.ChirpDestructiveConfirmationDialog
import com.filippo.core.designsystem.theme.ChirpTheme
import com.filippo.core.designsystem.theme.extended
import com.filippo.core.presentation.util.drawDivider
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatListScreenRoot(
    selectedChatId: String?,
    viewModel: ChatListViewModel = koinViewModel(),
    onChatClick: (chatId: String) -> Unit,
    onConfirmLogoutClick: () -> Unit,
    onCreateChatClick: () -> Unit,
    onProfileSettingsClick: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ChatListScreen(
        state = state,
        selectedChatId = selectedChatId,
        onChatClick = onChatClick,
        onConfirmLogoutClick = onConfirmLogoutClick,
        onCreateChatClick = onCreateChatClick,
        onProfileSettingsClick = onProfileSettingsClick,
    )
}

@Composable
fun ChatListScreen(
    state: ChatListState,
    selectedChatId: String?,
    onChatClick: (chatId: String) -> Unit,
    onConfirmLogoutClick: () -> Unit,
    onCreateChatClick: () -> Unit,
    onProfileSettingsClick: () -> Unit,
) {
    var showLogoutConfirmation by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.extended.surfaceLower,
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            ChatListHeader(
                modifier = Modifier.statusBarsPadding(),
                avatar = state.currentUser?.avatar,
                onLogoutClick = { showLogoutConfirmation = true },
                onProfileSettingsClick = onProfileSettingsClick,
            )
        },
        floatingActionButton = { CreateChatButton(onCreateChatClick) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading -> CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                state.chats.isEmpty() -> EmptyChatSection(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    title = stringResource(Res.string.no_messages),
                    description = stringResource(Res.string.no_messages_subtitle)
                )
                else -> LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(
                        items = state.chats,
                        key = { it.id },
                    ) { chat ->
                        ChatListItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onChatClick(chat.id) }
                                .drawDivider(color = MaterialTheme.colorScheme.outline),
                            chat = chat,
                            isSelected = chat.id == selectedChatId,
                        )
                    }
                }
            }
        }
    }

    if (showLogoutConfirmation) {
        ChirpDestructiveConfirmationDialog(
            title = stringResource(Res.string.do_you_want_to_logout),
            description = stringResource(Res.string.do_you_want_to_logout_desc),
            confirmButtonText = stringResource(Res.string.logout),
            cancelButtonText = stringResource(Res.string.cancel),
            onConfirmClick = onConfirmLogoutClick,
            onDismiss = { showLogoutConfirmation = false },
        )
    }
}

@Composable
private fun CreateChatButton(onClick: () -> Unit) {
    ChirpFloatingActionButton(onClick) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(Res.string.create_chat),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ChirpTheme {
        ChatListScreen(
            state = ChatListState(),
            selectedChatId = "",
            onChatClick = {},
            onConfirmLogoutClick = {},
            onCreateChatClick = {},
            onProfileSettingsClick = {}
        )
    }
}