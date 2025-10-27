package com.filippo.chat.presentation.chat_list_details.model

import androidx.compose.ui.text.AnnotatedString
import com.filippo.core.designsystem.components.avatar.AvatarUiModel
import com.filippo.core.presentation.util.UiText

data class ChatUiModel(
    val id: String,
    val title: UiText,
    val subtitle: UiText?,
    val avatars: List<AvatarUiModel>,
    val remainingAvatars: Int,
    val lastMessage: AnnotatedString?,
)