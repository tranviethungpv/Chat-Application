package com.example.chatapp1

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Message(
    var id: Int,
    var conversationId: Int,
    var senderId: Int,
    var text: String,
    var timestamp: Long,
    var isDeletedByUser1: Boolean = false,
    var isDeletedByUser2: Boolean = false,
) : Parcelable {
    fun readFromParcel(_reply: Parcel) {
        id = _reply.readInt()
        conversationId = _reply.readInt()
        senderId = _reply.readInt()
        text = _reply.readString() ?: ""
        timestamp = _reply.readLong()
        isDeletedByUser1 = _reply.readInt() != 0
        isDeletedByUser2 = _reply.readInt() != 0
    }
}