package com.filippo.chat.presentation.manage_chat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.create_chat
import com.filippo.chat.presentation.manage_chat.components.ManageChatSection
import com.filippo.core.designsystem.components.dialogs.ChirpAdaptiveDialogSheetLayout
import com.filippo.core.presentation.util.ObserveAsEvents
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CreateChatScreenRoot(
    viewModel: CreateChatViewModel = koinViewModel(),
    onChatCreated: (chatId: String) -> Unit,
    onDismiss: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.chatCreated, onEvent = onChatCreated)

    ChirpAdaptiveDialogSheetLayout(onDismiss) {
        ManageChatSection(
            state = state,
            primaryButtonText = stringResource(Res.string.create_chat),
            headerText = stringResource(Res.string.create_chat),
            onPrimaryButtonClick = viewModel::onCreateChatClick,
            onAddClick = viewModel::onAddParticipantClick,
            onDismiss = onDismiss,
        )
    }
}