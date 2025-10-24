package com.filippo.chat.presentation.chat_list_details.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.filippo.chat.domain.models.MessageDeliveryStatus
import com.filippo.core.designsystem.theme.extended
import com.filippo.core.presentation.util.UiText

sealed interface MessageUiModel {
    val id: String

    data class LocalUserMessage(
        override val id: String,
        val content: String,
        val deliveryStatus: MessageDeliveryStatus,
        val formattedSentTime: UiText,
    ) : MessageUiModel

    data class OtherUserMessage(
        override val id: String,
        val content: String,
        val formattedSentTime: UiText,
        val sender: ChatParticipantUiModel,
    ) : MessageUiModel {

        val background: Color
            @Composable
            get() {
                val colorPool = with(MaterialTheme.colorScheme.extended) {
                    listOf(cakeViolet, cakeGreen, cakePink, cakeOrange, cakeBlue, cakeYellow, cakePurple, cakeRed)
                }
                val index = sender.id.hashCode().toUInt() % colorPool.size.toUInt()
                return colorPool[index.toInt()]
            }
    }

    data class DateSeparator(
        override val id: String,
        val date: UiText,
    ) : MessageUiModel
}