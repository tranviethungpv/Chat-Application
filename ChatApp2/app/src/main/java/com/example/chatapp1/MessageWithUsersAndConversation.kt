package com.example.chatapp1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MessageWithUsersAndConversation(
    val message: Message,
    val sender: User,
    val receiver: User,
    val conversation: Conversation
) : Parcelable