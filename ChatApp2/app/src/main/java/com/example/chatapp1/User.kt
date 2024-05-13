package com.example.chatapp1

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id: Int = 0,
    var name: String = "",
    var avatar: String = "",
) : Parcelable {
    fun readFromParcel(_reply: Parcel) {
        id = _reply.readInt()
        name = _reply.readString() ?: ""
        avatar = _reply.readString() ?: ""
    }
}