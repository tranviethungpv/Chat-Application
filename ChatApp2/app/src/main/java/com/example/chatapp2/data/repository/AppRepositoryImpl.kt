package com.example.chatapp2.data.repository

import android.util.Log
import com.example.chatapp1.Conversation
import com.example.chatapp1.Message
import com.example.chatapp1.MessageWithReceiverAndConversationInfo
import com.example.chatapp1.MessageWithUsersAndConversation
import com.example.chatapp1.User
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val chatServiceConnection: ChatServiceConnection
) : AppRepository {
    override fun insertConversation(conversation: Conversation): Long {
        return chatServiceConnection.getChatService()?.insertConversation(conversation) ?: -1
    }

    override fun updateConversation(conversation: Conversation): Int {
        return chatServiceConnection.getChatService()?.updateConversation(conversation) ?: -1
    }

    override fun deleteConversation(conversation: Conversation): Int {
        return chatServiceConnection.getChatService()?.deleteConversation(conversation) ?: -1
    }

    override fun getAllConversations(): List<Conversation> {
        return chatServiceConnection.getChatService()?.allConversations ?: emptyList()
    }

    override fun getConversation(userId1: Int, userId2: Int): Conversation? {
        return chatServiceConnection.getChatService()?.getConversation(userId1, userId2)
    }

    override fun insertMessage(message: Message): Long {
        return chatServiceConnection.getChatService()?.insertMessage(message) ?: -1
    }

    override fun updateMessage(message: Message): Int {
        return chatServiceConnection.getChatService()?.updateMessage(message) ?: -1
    }

    override fun deleteMessage(message: Message): Int {
        return chatServiceConnection.getChatService()?.deleteMessage(message) ?: -1
    }

    override fun getAllMessages(): List<Message> {
        return chatServiceConnection.getChatService()?.allMessages ?: emptyList()
    }

    override fun insertUser(user: User): Long {
        return chatServiceConnection.getChatService()?.insertUser(user) ?: -1
    }

    override fun updateUser(user: User): Int {
        return chatServiceConnection.getChatService()?.updateUser(user) ?: -1
    }

    override fun deleteUser(user: User): Int {
        return chatServiceConnection.getChatService()?.deleteUser(user) ?: -1
    }

    override fun getAllUsers(): List<User> {
        return chatServiceConnection.getChatService()?.allUsers ?: emptyList()
    }

    override fun getUserById(id: Int): User {
        return chatServiceConnection.getChatService()?.getUserById(id) ?: User()
    }

    override fun getMessagesWithUsersAndConversation(conversationId: Int): List<MessageWithUsersAndConversation> {
        return chatServiceConnection.getChatService()
            ?.getMessagesWithUsersAndConversation(conversationId) ?: emptyList()
    }

    override fun getLatestMessagesWithReceiverAndConversationInfo(userId1: Int): List<MessageWithReceiverAndConversationInfo> {
        Log.d("AppRepositoryImpl", "getLatestMessagesWithReceiverAndConversationInfo")
        return chatServiceConnection.getChatService()
            ?.getMessagesWithReceiverAndConversationInfo(userId1) ?: emptyList()
    }
}