package com.example.chatapp1

import android.os.Parcelable
import androidx.room.Embedded
import kotlinx.parcelize.Parcelize

@Parcelize
data class MessageWithReceiverAndConversationInfo(
    @Embedded(prefix = "message_") val latestMessage: Message,
    @Embedded(prefix = "receiver_") val receiver: User,
    @Embedded(prefix = "conversation_") val conversation: Conversation
) : Parcelable