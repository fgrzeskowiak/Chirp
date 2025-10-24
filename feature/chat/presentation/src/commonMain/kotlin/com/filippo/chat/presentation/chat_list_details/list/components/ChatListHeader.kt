package com.filippo.chat.presentation.chat_list_details.list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chirp.core.designsystem.generated.resources.logo_chirp
import chirp.core.designsystem.generated.resources.logout_icon
import chirp.core.designsystem.generated.resources.users_icon
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.logout
import chirp.feature.chat.presentation.generated.resources.profile_settings
import com.filippo.chat.presentation.components.ChatHeader
import com.filippo.core.designsystem.components.avatar.AvatarUiModel
import com.filippo.core.designsystem.components.avatar.ChirpAvatarPhoto
import com.filippo.core.designsystem.components.dropdown.ChirpDropdownMenu
import com.filippo.core.designsystem.components.dropdown.DropdownMenuItem
import com.filippo.core.designsystem.theme.extended
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import chirp.core.designsystem.generated.resources.Res as DesignSystemRes

@Composable
fun ChatListHeader(
    avatar: AvatarUiModel?,
    onProfileSettingsClick: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ChatHeader(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = vectorResource(DesignSystemRes.drawable.logo_chirp),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.tertiary
            )
            Text(
                text = "Chirp",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.extended.textPrimary
            )
            Spacer(modifier = Modifier.weight(1f))
            ProfileAvatarSection(
                avatar = avatar,
                onProfileSettingsClick = onProfileSettingsClick,
                onLogoutClick = onLogoutClick,
            )
        }
    }
}

@Composable
fun ProfileAvatarSection(
    avatar: AvatarUiModel?,
    onProfileSettingsClick: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isMenuOpen by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        avatar?.let { ChirpAvatarPhoto(avatar = it, onClick = { isMenuOpen = true }) }
        ChirpDropdownMenu(
            isOpen = isMenuOpen,
            onDismiss = { isMenuOpen = false },
            items = listOf(
                DropdownMenuItem(
                    icon = vectorResource(DesignSystemRes.drawable.users_icon),
                    text = stringResource(Res.string.profile_settings),
                    contentColor = MaterialTheme.colorScheme.extended.textSecondary,
                    onClick = onProfileSettingsClick,
                ),
                DropdownMenuItem(
                    icon = vectorResource(DesignSystemRes.drawable.logout_icon),
                    text = stringResource(Res.string.logout),
                    contentColor = MaterialTheme.colorScheme.extended.destructiveHover,
                    onClick = onLogoutClick,
                ),
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ChatListHeader(
        avatar = AvatarUiModel(displayText = "PL"),
        onProfileSettingsClick = {},
        onLogoutClick = {}
    )
}