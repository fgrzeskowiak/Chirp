package com.filippo.chat.presentation.mappers

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.group_chat
import chirp.feature.chat.presentation.generated.resources.you
import com.filippo.chat.domain.models.Chat
import com.filippo.chat.presentation.chat_list_details.model.ChatUiModel
import com.filippo.core.designsystem.components.avatar.AvatarUiModel
import com.filippo.core.presentation.util.UiText

fun Chat.toUiModel(userId: String): ChatUiModel {
    val otherParticipants = participants.filter { it.userId != userId }
    return ChatUiModel(
        id = id,
        title = if (otherParticipants.size > 1) {
            UiText.Resource(Res.string.group_chat)
        } else {
            UiText.Dynamic(otherParticipants.first().username)
        },
        subtitle = otherParticipants.takeIf { it.size > 1 }?.let { others ->
            UiText.Combined(
                listOf(
                    UiText.Resource(Res.string.you),
                    UiText.Dynamic(others.joinToString { it.username })
                )
            )
        },
        avatars = otherParticipants.take(2).map {
            AvatarUiModel(
                displayText = it.initials,
                imageUrl = it.profilePictureUrl,
            )
        },
        remainingAvatars = (otherParticipants.size - 2).coerceAtLeast(0),
        lastMessage = lastMessage?.let { lastMessage ->
            participants.find { it.userId == lastMessage.senderId }?.let { sender ->
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("${sender.username}:")
                    }
                    append(lastMessage.content)
                }
            }
        },
    )
}