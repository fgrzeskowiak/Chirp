package com.filippo.chat.presentation.mappers

import com.filippo.chat.domain.models.ChatParticipant
import com.filippo.chat.presentation.chat_list_details.model.ChatParticipantUiModel
import com.filippo.core.designsystem.components.avatar.AvatarUiModel
import com.filippo.core.domain.auth.User

fun ChatParticipant.toUiModel() = ChatParticipantUiModel(
    id = userId,
    username = username,
    avatar = AvatarUiModel(displayText = initials, imageUrl = profilePictureUrl),
)

fun User.toUiModel() = ChatParticipantUiModel(
    id = id,
    username = username,
    avatar = AvatarUiModel(
        displayText = username.take(2).uppercase(),
        imageUrl = profilePictureUrl
    ),
)