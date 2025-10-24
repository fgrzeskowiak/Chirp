package com.filippo.chat.presentation.chat_list_details

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.filippo.chat.presentation.chat_list_details.ChatListDetailsState.DialogState
import com.filippo.chat.presentation.chat_list_details.details.ChatDetailsScreenRoot
import com.filippo.chat.presentation.chat_list_details.list.ChatListScreenRoot
import com.filippo.chat.presentation.create_chat.CreateChatScreenRoot
import com.filippo.core.designsystem.theme.ChirpTheme
import com.filippo.core.designsystem.theme.extended
import com.filippo.core.presentation.util.DialogSheetScopedViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatListDetailsScreenRoot(
    viewModel: ChatListDetailsViewModel = koinViewModel(),
    onLogout: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ChatListDetailsScreen(
        state = state,
        onLogout = onLogout,
        onAction = viewModel::onAction
    )
}

@Composable
@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalComposeUiApi::class)
fun ChatListDetailsScreen(
    state: ChatListDetailsState,
    onLogout: () -> Unit,
    onAction: (ChatListDetailsAction) -> Unit,
) {
    val scaffoldDirective = createNoSpacingPaneScaffoldDirective()
    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator(scaffoldDirective)
    val listPane = scaffoldNavigator.scaffoldValue[ListDetailPaneScaffoldRole.List]
    val detailsPane = scaffoldNavigator.scaffoldValue[ListDetailPaneScaffoldRole.Detail]
    val scope = rememberCoroutineScope()

    BackHandler(enabled = scaffoldNavigator.canNavigateBack()) {
        onAction(ChatListDetailsAction.OnChatClick(null))
        scope.launch { scaffoldNavigator.navigateBack() }
    }

    ListDetailPaneScaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.extended.surfaceLower),
        directive = scaffoldDirective,
        scaffoldState = scaffoldNavigator.scaffoldState,
        listPane = {
            AnimatedPane {
                ChatListScreenRoot(
                    selectedChatId = state.selectedChatId,
                    onChatClick = { chatId ->
                        onAction(ChatListDetailsAction.OnChatClick(chatId))
                        scope.launch {
                            scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                        }
                    },
                    onConfirmLogoutClick = onLogout,
                    onCreateChatClick = { onAction(ChatListDetailsAction.OnCreateChatClick) },
                    onProfileSettingsClick = { onAction(ChatListDetailsAction.OnProfileDetailsClick) }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                ChatDetailsScreenRoot(
                    chatId = state.selectedChatId,
                    showBackButton = listPane == PaneAdaptedValue.Hidden && detailsPane == PaneAdaptedValue.Expanded,
                    onBackClick = {
                        onAction(ChatListDetailsAction.OnChatClick(null))
                        scope.launch { scaffoldNavigator.navigateBack() }
                    },
                )
            }
        }
    )

    DialogSheetScopedViewModel(
        isVisible = state.dialogState is DialogState.CreateChat
    ) {
        CreateChatScreenRoot(
            onChatCreated = {
                onAction(ChatListDetailsAction.OnDismissCurrentDialog)
                onAction(ChatListDetailsAction.OnChatClick(it.id))
                scope.launch {
                    scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                }
            },
            onDismiss = { onAction(ChatListDetailsAction.OnDismissCurrentDialog) }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ChirpTheme {
        ChatListDetailsScreen(
            state = ChatListDetailsState(),
            onLogout = {},
            onAction = {},
        )
    }
}