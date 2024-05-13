package com.example.chatapp1

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var name: String,
    var avatar: String,
) : Parcelable {
    fun readFromParcel(_reply: Parcel) {
        id = _reply.readInt()
        name = _reply.readString() ?: ""
        avatar = _reply.readString() ?: ""
    }
}