package com.filippo.chat.presentation.chat_list_details.details

import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filippo.chat.domain.models.MessageDeliveryStatus
import com.filippo.chat.presentation.chat_list_details.model.ChatParticipantUiModel
import com.filippo.chat.presentation.chat_list_details.model.ChatUiModel
import com.filippo.chat.presentation.chat_list_details.model.MessageUiModel
import com.filippo.core.designsystem.components.avatar.AvatarUiModel
import com.filippo.core.presentation.util.UiText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ChatDetailsViewModel : ViewModel() {

    private val state = MutableStateFlow(ChatDetailsState())
    val uiState: StateFlow<ChatDetailsState> = state

    private val chats = listOf(
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
    )

    private val messages = mapOf(
        "1" to List(20) {
            if (it % 2 == 0) {
                MessageUiModel.LocalUserMessage(
                    id = it.toString(),
                    content = "Hello world!",
                    deliveryStatus = MessageDeliveryStatus.SENT,
                    formattedSentTime = UiText.Dynamic("Friday, Aug 20")
                )
            } else {
                MessageUiModel.OtherUserMessage(
                    id = it.toString(),
                    content = "Hello world!",
                    sender = ChatParticipantUiModel(
                        id = "1",
                        username = "Bolek",
                        avatar = AvatarUiModel("BO")
                    ),
                    formattedSentTime = UiText.Dynamic("Friday, Aug 20"),
                )
            }
        },
        "2" to List(2) {
            if (it % 2 == 0) {
                MessageUiModel.LocalUserMessage(
                    id = it.toString(),
                    content = "Hello world!",
                    deliveryStatus = MessageDeliveryStatus.SENT,
                    formattedSentTime = UiText.Dynamic("Friday, Aug 20")
                )
            } else {
                MessageUiModel.OtherUserMessage(
                    id = it.toString(),
                    content = "Hello world!",
                    sender = ChatParticipantUiModel(
                        id = "1",
                        username = "Bolek",
                        avatar = AvatarUiModel("BO")
                    ),
                    formattedSentTime = UiText.Dynamic("Friday, Aug 20"),
                )
            }
        }
    )

    fun onAction(action: ChatDetailsAction) {
        when (action) {
            else -> Unit
        }
    }

    fun onChatSelected(chatId: String?) {
        viewModelScope.launch {
            state.update { it.copy(isLoading = true) }
            delay(500)
            state.update {
                it.copy(
                    chat = chats.find { chat -> chat.id == chatId },
                    messages = messages[chatId].orEmpty()
                )
            }
        }
    }
}