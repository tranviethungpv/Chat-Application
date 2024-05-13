package com.example.chatapp2.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.chatapp1.Message
import com.example.chatapp1.MessageWithUsersAndConversation
import com.example.chatapp2.data.repository.AppRepository
import com.example.chatapp2.data.repository.ChatServiceConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val appRepository: AppRepository, chatServiceConnection: ChatServiceConnection
) : ViewModel() {

    init {
        chatServiceConnection.serviceConnected.observeForever {
            if (it) {
                Log.d("ConversationViewModel", "Service connected")
            }
        }
    }

    suspend fun insertMessage(message: Message): Boolean {
        var result: Long
        withContext(Dispatchers.IO) {
            result = appRepository.insertMessage(message)
        }
        return result != -1L
    }

    suspend fun updateMessage(message: Message): Boolean {
        var result: Int
        withContext(Dispatchers.IO) {
            result = appRepository.updateMessage(message)
        }
        return result > 0
    }

    suspend fun deleteMessage(message: Message): Boolean {
        var result: Int
        withContext(Dispatchers.IO) {
            result = appRepository.deleteMessage(message)
        }
        return result > 0
    }

    fun getMessagesWithUsersAndConversation(
        conversationId: Int
    ): LiveData<List<MessageWithUsersAndConversation>> {
        return liveData(Dispatchers.IO) {
            emit(appRepository.getMessagesWithUsersAndConversation(conversationId))
        }
    }
}