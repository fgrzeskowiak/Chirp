package com.filippo.chat.presentation.chat_list_details.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import chirp.core.designsystem.generated.resources.logout_icon
import chirp.core.designsystem.generated.resources.users_icon
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.chat_members
import chirp.feature.chat.presentation.generated.resources.go_back
import chirp.feature.chat.presentation.generated.resources.leave_chat
import chirp.feature.chat.presentation.generated.resources.open_chat_options_menu
import com.filippo.chat.presentation.chat_list_details.model.ChatUiModel
import com.filippo.chat.presentation.components.ChatSummary
import com.filippo.core.designsystem.components.avatar.AvatarUiModel
import com.filippo.core.designsystem.components.buttons.ChirpIconButton
import com.filippo.core.designsystem.components.dropdown.ChirpDropdownMenu
import com.filippo.core.designsystem.components.dropdown.DropdownMenuItem
import com.filippo.core.designsystem.theme.ChirpTheme
import com.filippo.core.designsystem.theme.extended
import com.filippo.core.presentation.util.UiText
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import chirp.core.designsystem.generated.resources.Res as DesignSystemRes

@Composable
fun ChatDetailHeader(
    chat: ChatUiModel?,
    showBackButton: Boolean,
    onChatOptionsClick: () -> Unit,
    onManageChatClick: () -> Unit,
    onLeaveChatClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (showBackButton) {
            ChirpIconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(Res.string.go_back),
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.extended.textSecondary
                )
            }
        }

        if (chat != null) {
            ChatSummary(
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = onManageChatClick),
                chat = chat
            )
        } else {
            Spacer(Modifier.weight(1f))
        }

        Box {
            ChirpIconButton(onChatOptionsClick) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(Res.string.open_chat_options_menu),
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.extended.textSecondary
                )
            }

            var isMenuOpen by remember { mutableStateOf(false) }

            ChirpDropdownMenu(
                isOpen = isMenuOpen,
                onDismiss = { isMenuOpen = false },
                items = listOf(
                    DropdownMenuItem(
                        text = stringResource(Res.string.chat_members),
                        icon = vectorResource(DesignSystemRes.drawable.users_icon),
                        contentColor = MaterialTheme.colorScheme.extended.textSecondary,
                        onClick = onManageChatClick
                    ),
                    DropdownMenuItem(
                        text = stringResource(Res.string.leave_chat),
                        icon = vectorResource(DesignSystemRes.drawable.logout_icon),
                        contentColor = MaterialTheme.colorScheme.extended.destructiveHover,
                        onClick = onLeaveChatClick
                    ),
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ChirpTheme {
        ChatDetailHeader(
            modifier = Modifier.padding(8.dp),
            chat = ChatUiModel(
                id = "1",
                title = UiText.Dynamic("Group Chat"),
                subtitle = UiText.Dynamic("Bolek i Lolek"),
                avatars = listOf(AvatarUiModel("BO"), AvatarUiModel("LO")),
                lastMessage = AnnotatedString("Last message"),
            ),
            showBackButton = true,
            onChatOptionsClick = {},
            onManageChatClick = {},
            onLeaveChatClick = {},
            onBackClick = {},
        )
    }
}