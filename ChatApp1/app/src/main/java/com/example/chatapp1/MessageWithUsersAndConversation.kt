package com.example.chatapp1

import android.os.Parcelable
import androidx.room.Embedded
import kotlinx.parcelize.Parcelize

@Parcelize
data class MessageWithUsersAndConversation(
    @Embedded(prefix = "message_") val message: Message,
    @Embedded(prefix = "user1_") val sender: User,
    @Embedded(prefix = "user2_") val receiver: User,
    @Embedded(prefix = "conversation_") val conversation: Conversation
) : Parcelable