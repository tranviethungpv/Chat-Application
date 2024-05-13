package com.example.chatapp1

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "conversations",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId1"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId2"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index(value = ["userId1", "userId2"], unique = true)]
)
data class Conversation(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var userId1: Int,
    var userId2: Int,
) : Parcelable {
    fun readFromParcel(_reply: Parcel) {
        id = _reply.readInt()
        userId1 = _reply.readInt()
        userId2 = _reply.readInt()
    }
}