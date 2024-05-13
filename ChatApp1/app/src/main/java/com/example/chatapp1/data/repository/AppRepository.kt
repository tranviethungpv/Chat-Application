package com.example.chatapp1.data.repository

import com.example.chatapp1.Conversation
import com.example.chatapp1.Message
import com.example.chatapp1.MessageWithReceiverAndConversationInfo
import com.example.chatapp1.MessageWithUsersAndConversation
import com.example.chatapp1.User

interface AppRepository {
    fun insertConversation(conversation: Conversation): Long
    fun updateConversation(conversation: Conversation): Int
    fun deleteConversation(conversation: Conversation): Int
    fun getAllConversations(): List<Conversation>
    fun getConversation(userId1: Int, userId2: Int): Conversation

    fun insertMessage(message: Message): Long
    fun updateMessage(message: Message): Int
    fun deleteMessage(message: Message): Int
    fun getAllMessages(): List<Message>

    fun insertUser(user: User): Long
    fun updateUser(user: User): Int
    fun deleteUser(user: User): Int
    fun getAllUsers(): List<User>
    fun getUserById(id: Int): User

    fun getMessagesWithUsersAndConversation(
        conversationId: Int
    ): List<MessageWithUsersAndConversation>
    fun getLatestMessagesWithReceiverAndConversationInfo(userId1: Int): List<MessageWithReceiverAndConversationInfo>
}