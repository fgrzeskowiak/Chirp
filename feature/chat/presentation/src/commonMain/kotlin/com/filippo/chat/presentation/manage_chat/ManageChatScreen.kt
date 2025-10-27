package com.filippo.chat.presentation.manage_chat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.chat_members
import chirp.feature.chat.presentation.generated.resources.save
import com.filippo.chat.presentation.manage_chat.components.ManageChatSection
import com.filippo.core.designsystem.components.dialogs.ChirpAdaptiveDialogSheetLayout
import com.filippo.core.presentation.util.ObserveAsEvents
import org.jetbrains.compose.resources.stringResource

@Composable
fun ManageChatScreenRoot(
    viewModel: ManageChatViewModel,
    onMembersAdded: () -> Unit,
    onDismiss: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()

    ObserveAsEvents(viewModel.membersAdded, onEvent = { onMembersAdded() })

    ChirpAdaptiveDialogSheetLayout(onDismiss) {
        ManageChatSection(
            state = state,
            primaryButtonText = stringResource(Res.string.save),
            headerText = stringResource(Res.string.chat_members),
            onPrimaryButtonClick = viewModel::onSaveClick,
            onAddClick = viewModel::onAddParticipantClick,
            onDismiss = onDismiss,
        )
    }
}