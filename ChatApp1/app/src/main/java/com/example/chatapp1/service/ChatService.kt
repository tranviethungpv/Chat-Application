package com.example.chatapp1.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.chatapp1.Conversation
import com.example.chatapp1.data.repository.AppRepository
import javax.inject.Inject
import com.example.chatapp1.IChatService
import com.example.chatapp1.Message
import com.example.chatapp1.MessageWithReceiverAndConversationInfo
import com.example.chatapp1.MessageWithUsersAndConversation
import com.example.chatapp1.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatService : Service() {
    @Inject
    lateinit var appRepository: AppRepository

    private val binder = object : IChatService.Stub() {
        override fun insertConversation(conversation: Conversation): Long {
            return appRepository.insertConversation(conversation)
        }

        override fun updateConversation(conversation: Conversation): Int {
            return appRepository.updateConversation(conversation)
        }

        override fun deleteConversation(conversation: Conversation): Int {
            return appRepository.deleteConversation(conversation)
        }

        override fun getAllConversations(): List<Conversation> {
            return appRepository.getAllConversations()
        }

        override fun getConversation(userId1: Int, userId2: Int): Conversation {
            Log.d("ChatService", "getConversation($userId1, $userId2)")
            return appRepository.getConversation(userId1, userId2)
        }

        override fun hideMessagesInConversation(conversation: Conversation?, userId: Int): Int {
            return appRepository.hideMessagesInConversation(conversation!!, userId)
        }

        override fun insertMessage(message: Message): Long {
            return appRepository.insertMessage(message)
        }

        override fun updateMessage(message: Message): Int {
            return appRepository.updateMessage(message)
        }

        override fun deleteMessage(message: Message): Int {
            return appRepository.deleteMessage(message)
        }

        override fun getAllMessages(): List<Message> {
            return appRepository.getAllMessages()
        }

        override fun insertUser(user: User): Long {
            return appRepository.insertUser(user)
        }

        override fun updateUser(user: User): Int {
            return appRepository.updateUser(user)
        }

        override fun deleteUser(user: User): Int {
            return appRepository.deleteUser(user)
        }

        override fun getAllUsers(): List<User> {
            Log.d("ChatService", "${appRepository.getAllUsers()}")
            return appRepository.getAllUsers()
        }

        override fun getUserById(id: Int): User {
            return appRepository.getUserById(id)
        }

        override fun getMessagesWithUsersAndConversation(
            conversationId: Int, userId: Int
        ): List<MessageWithUsersAndConversation> {
            return appRepository.getMessagesWithUsersAndConversation(conversationId, userId)
        }

        override fun getMessagesWithReceiverAndConversationInfo(
            userId1: Int
        ): List<MessageWithReceiverAndConversationInfo> {
            return appRepository.getLatestMessagesWithReceiverAndConversationInfo(userId1)
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }
}