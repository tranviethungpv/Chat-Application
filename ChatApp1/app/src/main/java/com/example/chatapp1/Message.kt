package com.example.chatapp1

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "messages",
    foreignKeys = [
        ForeignKey(
            entity = Conversation::class,
            parentColumns = ["id"],
            childColumns = ["conversationId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["senderId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class Message(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var conversationId: Int,
    var senderId: Int,
    var text: String,
    var timestamp: Long
) : Parcelable {
    fun readFromParcel(_reply: Parcel) {
        id = _reply.readInt()
        conversationId = _reply.readInt()
        senderId = _reply.readInt()
        text = _reply.readString() ?: ""
        timestamp = _reply.readLong()
    }
}