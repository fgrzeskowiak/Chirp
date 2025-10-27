package com.filippo.chat.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.filippo.chat.presentation.chat_list_details.model.ChatParticipantUiModel
import com.filippo.core.designsystem.components.avatar.ChirpAvatarPhoto
import com.filippo.core.designsystem.components.brand.ChirpHorizontalDivider
import com.filippo.core.designsystem.theme.extended
import com.filippo.core.designsystem.theme.titleXSmall
import com.filippo.core.presentation.util.DeviceConfiguration
import com.filippo.core.presentation.util.currentDeviceConfiguration

@Composable
fun ColumnScope.ChatParticipantsSelectionSection(
    participants: List<ChatParticipantUiModel>,
    existingParticipants: List<ChatParticipantUiModel>,
    modifier: Modifier = Modifier,
    searchResult: ChatParticipantUiModel? = null,
) {
    val deviceConfiguration = currentDeviceConfiguration()
    val rootHeightModifier = when (deviceConfiguration) {
        DeviceConfiguration.TABLET_PORTRAIT,
        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP,
            -> {
            Modifier
                .animateContentSize()
                .heightIn(min = 200.dp, max = 300.dp)
        }

        else -> Modifier.weight(1f)
    }

    Box(modifier = rootHeightModifier.then(modifier)) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(
                items = existingParticipants,
                key = { "existing_${it.id}" }
            ) { participant ->
                ChatParticipantListItem(
                    modifier = Modifier.fillMaxWidth(),
                    participant = participant
                )
            }

            if (existingParticipants.isNotEmpty()) {
                item { ChirpHorizontalDivider() }
            }
            if (searchResult == null) {
                items(
                    items = participants,
                    key = { it.id }
                ) { participant ->
                    ChatParticipantListItem(
                        modifier = Modifier.fillMaxWidth(),
                        participant = participant
                    )
                }
            } else {
                item(key = searchResult.id) {
                    ChatParticipantListItem(
                        modifier = Modifier.fillMaxWidth(),
                        participant = searchResult
                    )
                }
            }
        }
    }
}

@Composable
fun ChatParticipantListItem(
    participant: ChatParticipantUiModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ChirpAvatarPhoto(participant.avatar)
        Text(
            text = participant.username,
            style = MaterialTheme.typography.titleXSmall,
            color = MaterialTheme.colorScheme.extended.textPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}