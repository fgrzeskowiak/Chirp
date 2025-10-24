package com.filippo.chat.presentation.chat_list_details.model

import com.filippo.core.designsystem.components.avatar.AvatarUiModel

data class ChatParticipantUiModel(
    val id: String,
    val username: String,
    val avatar: AvatarUiModel,
)
