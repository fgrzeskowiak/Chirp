package com.filippo.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ParticipantsRequest(
    val userIds: List<String>
)
