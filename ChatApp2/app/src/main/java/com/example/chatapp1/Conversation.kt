package com.example.chatapp1

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Conversation(
    var id: Int = 0,
    var userId1: Int = 0,
    var userId2: Int = 0,
) : Parcelable {
    fun readFromParcel(_reply: Parcel) {
        id = _reply.readInt()
        userId1 = _reply.readInt()
        userId2 = _reply.readInt()
    }
}