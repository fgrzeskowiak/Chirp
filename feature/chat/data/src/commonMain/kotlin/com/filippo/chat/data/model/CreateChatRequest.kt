package com.filippo.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateChatRequest(val otherUserIds: List<String>)