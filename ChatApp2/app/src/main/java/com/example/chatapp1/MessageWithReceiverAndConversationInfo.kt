package com.example.chatapp1

import android.os.Parcelable
import androidx.room.Embedded
import kotlinx.parcelize.Parcelize

@Parcelize
data class MessageWithReceiverAndConversationInfo(
    val latestMessage: Message,
    val receiver: User,
    val conversation: Conversation
) : Parcelable