package com.example.chatapp1.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.chatapp1.Message

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(message: Message): Long

    @Update
    fun update(message: Message): Int

    @Delete
    fun delete(message: Message): Int

    @Query("SELECT * FROM messages")
    fun getAll(): List<Message>

    @Query("SELECT * FROM messages WHERE conversationId = :conversationId")
    fun getMessagesByConversationId(conversationId: Int): List<Message>
}