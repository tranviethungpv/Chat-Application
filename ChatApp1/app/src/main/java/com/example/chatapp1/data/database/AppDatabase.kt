package com.example.chatapp1.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.chatapp1.Conversation
import com.example.chatapp1.Message
import com.example.chatapp1.User
import com.example.chatapp1.data.dao.AppDao
import com.example.chatapp1.data.dao.ConversationDao
import com.example.chatapp1.data.dao.MessageDao
import com.example.chatapp1.data.dao.UserDao

@Database(entities = [Conversation::class, User::class, Message::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun conversationDao(): ConversationDao
    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao
    abstract fun appDao(): AppDao
}