package com.filippo.chat.presentation.manage_chat.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.cancel
import com.filippo.chat.presentation.components.ChatParticipantSearchTextSection
import com.filippo.chat.presentation.components.ChatParticipantsSelectionSection
import com.filippo.chat.presentation.components.ManageChatHeaderRow
import com.filippo.chat.presentation.manage_chat.ManageChatState
import com.filippo.core.designsystem.components.brand.ChirpHorizontalDivider
import com.filippo.core.designsystem.components.buttons.ChirpButton
import com.filippo.core.designsystem.components.buttons.ChirpButtonStyle
import com.filippo.core.designsystem.theme.ChirpTheme
import com.filippo.core.presentation.util.DeviceConfiguration
import com.filippo.core.presentation.util.clearFocusOnTap
import com.filippo.core.presentation.util.currentDeviceConfiguration
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ManageChatSection(
    state: ManageChatState,
    primaryButtonText: String,
    headerText: String,
    onPrimaryButtonClick: () -> Unit,
    onAddClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    var isTextFieldFocused by remember { mutableStateOf(false) }
    val imeHeight = WindowInsets.ime.getBottom(LocalDensity.current)
    val isKeyboardVisible = imeHeight > 0
    val configuration = currentDeviceConfiguration()

    val shouldHideHeader = configuration == DeviceConfiguration.MOBILE_LANDSCAPE ||
            isKeyboardVisible || isTextFieldFocused

    Column(
        modifier = Modifier
            .clearFocusOnTap()
            .fillMaxWidth()
            .imePadding()
            .background(MaterialTheme.colorScheme.surface)
            .navigationBarsPadding()
    ) {
        AnimatedVisibility(!shouldHideHeader) {
            Column {
                ManageChatHeaderRow(
                    modifier = Modifier.fillMaxWidth(),
                    title = headerText,
                    onCloseClick = onDismiss,
                )
                ChirpHorizontalDivider()
            }
        }
        ChatParticipantSearchTextSection(
            modifier = Modifier.fillMaxWidth(),
            query = state.query,
            onAddClick = onAddClick,
            isSearchEnabled = state.canAddParticipant,
            isLoading = state.isSearching,
            error = state.searchError,
            onFocusChanged = { isTextFieldFocused = it }
        )
        ChirpHorizontalDivider()
        ChatParticipantsSelectionSection(
            modifier = Modifier.fillMaxWidth(),
            participants = state.selectedParticipants,
            existingParticipants = state.existingParticipants,
            searchResult = state.searchResult,
        )
        ButtonsSection(
            state = state,
            primaryButtonText = primaryButtonText,
            onCreateChatClick = onPrimaryButtonClick,
            onDismiss = onDismiss,
        )
    }
}

@Composable
private fun ButtonsSection(
    state: ManageChatState,
    primaryButtonText: String,
    onCreateChatClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End)
        ) {
            ChirpButton(
                text = stringResource(Res.string.cancel),
                onClick = onDismiss,
                style = ChirpButtonStyle.SECONDARY
            )
            ChirpButton(
                text = primaryButtonText,
                onClick = onCreateChatClick,
                isEnabled = state.selectedParticipants.isNotEmpty(),
                isLoading = state.isCreatingChat,
            )
        }
        AnimatedVisibility(state.createChatError != null) {
            Spacer(Modifier.height(16.dp))
            state.createChatError?.let {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = it.asString(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.End,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ChirpTheme {
        ManageChatSection(
            state = ManageChatState(),
            headerText = "Create Chat",
            primaryButtonText = "Create",
            onPrimaryButtonClick = {},
            onAddClick = {},
            onDismiss = {},
        )
    }
}