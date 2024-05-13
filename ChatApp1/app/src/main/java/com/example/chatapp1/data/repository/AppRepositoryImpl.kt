package com.example.chatapp1.data.repository

import android.util.Log
import com.example.chatapp1.Conversation
import com.example.chatapp1.Message
import com.example.chatapp1.MessageWithReceiverAndConversationInfo
import com.example.chatapp1.MessageWithUsersAndConversation
import com.example.chatapp1.User
import com.example.chatapp1.data.dao.AppDao
import com.example.chatapp1.data.dao.MessageDao
import com.example.chatapp1.data.dao.UserDao
import com.example.chatapp1.data.dao.ConversationDao
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val conversationDao: ConversationDao,
    private val messageDao: MessageDao,
    private val userDao: UserDao,
    private val appDao: AppDao
) : AppRepository {
    override fun insertConversation(conversation: Conversation): Long {
        return conversationDao.insert(conversation)
    }

    override fun updateConversation(conversation: Conversation): Int {
        return conversationDao.update(conversation)
    }

    override fun deleteConversation(conversation: Conversation): Int {
        return conversationDao.delete(conversation)
    }

    override fun getAllConversations(): List<Conversation> {
        return conversationDao.getAll()
    }

    override fun getConversation(userId1: Int, userId2: Int): Conversation {
        Log.d("AppRepositoryImpl", "getConversation($userId1, $userId2)")
        return conversationDao.getConversation(userId1, userId2)
    }

    override fun hideMessagesInConversation(conversation: Conversation, userId: Int): Int {
        val messages = messageDao.getMessagesByConversationId(conversation.id)
        var updatedCount = 0
        for (message in messages) {
            if (userId == conversation.userId1) {
                message.isDeletedByUser1 = true
                //message.isDeletedByUser2 = true
            } else if (userId == conversation.userId2) {
                message.isDeletedByUser2 = true
            }
            updatedCount += messageDao.update(message)
        }
        return updatedCount
    }

    override fun insertMessage(message: Message): Long {
        return messageDao.insert(message)
    }

    override fun updateMessage(message: Message): Int {
        return messageDao.update(message)
    }

    override fun deleteMessage(message: Message): Int {
        return messageDao.delete(message)
    }

    override fun getAllMessages(): List<Message> {
        return messageDao.getAll()
    }

    override fun insertUser(user: User): Long {
        return userDao.insert(user)
    }

    override fun updateUser(user: User): Int {
        return userDao.update(user)
    }

    override fun deleteUser(user: User): Int {
        return userDao.delete(user)
    }

    override fun getAllUsers(): List<User> {
        return userDao.getAll()
    }

    override fun getUserById(id: Int): User {
        return userDao.getById(id)
    }

    override fun getMessagesWithUsersAndConversation(
        conversationId: Int, userId: Int
    ): List<MessageWithUsersAndConversation> {
        return appDao.getMessagesWithUsersAndConversation(conversationId, userId)
    }

    override fun getLatestMessagesWithReceiverAndConversationInfo(userId1: Int): List<MessageWithReceiverAndConversationInfo> {
        Log.d("AppRepositoryImpl", "getLatestMessagesWithReceiverAndConversationInfo($userId1)")
        return appDao.getLatestMessageWithReceiverInfo(userId1)
    }
}