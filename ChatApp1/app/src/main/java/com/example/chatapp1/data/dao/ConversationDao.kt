package com.example.chatapp1.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.chatapp1.Conversation

@Dao
interface ConversationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(conversation: Conversation): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(conversation: Conversation): Int

    @Delete
    fun delete(conversation: Conversation): Int

    @Query("SELECT * FROM conversations")
    fun getAll(): List<Conversation>

    @Query("SELECT * FROM conversations WHERE (userId1 = :userId1 AND userId2 = :userId2) OR (userId1 = :userId2 AND userId2 = :userId1)")
    fun getConversation(userId1: Int, userId2: Int): Conversation
}