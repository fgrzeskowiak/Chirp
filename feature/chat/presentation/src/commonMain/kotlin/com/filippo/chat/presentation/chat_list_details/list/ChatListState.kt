package com.filippo.chat.presentation.chat_list_details.list

import androidx.compose.ui.text.AnnotatedString
import com.filippo.chat.presentation.chat_list_details.model.ChatParticipantUiModel
import com.filippo.chat.presentation.chat_list_details.model.ChatUiModel
import com.filippo.core.designsystem.components.avatar.AvatarUiModel
import com.filippo.core.presentation.util.UiText

data class ChatListState(
    val chats: List<ChatUiModel> = listOf(
        ChatUiModel(
            id = "1",
            title = UiText.Dynamic("Group Chat"),
            subtitle = UiText.Dynamic("You, Bolek, Lolek"),
            avatars = listOf(AvatarUiModel("BO"), AvatarUiModel("LO")),
            lastMessage = AnnotatedString(
                "This is a last chat message that was sent by Philipp " +
                        "and goes over multiple lines to showcase the ellipsis"
            ),
        ),
        ChatUiModel(
            id = "2",
            title = UiText.Dynamic("Tom"),
            subtitle = UiText.Dynamic("You, Tom"),
            avatars = listOf(AvatarUiModel("TO")),
            lastMessage = AnnotatedString(
                "This is a last chat message that was sent by Philipp " +
                        "and goes over multiple lines to showcase the ellipsis"
            ),
        )
    ),
    val error: UiText? = null,
    val currentUser: ChatParticipantUiModel? = ChatParticipantUiModel(
        id = "di",
        username = "Tom",
        avatar = AvatarUiModel(displayText = "TO")
    ),
    val isLoading: Boolean = false,
)