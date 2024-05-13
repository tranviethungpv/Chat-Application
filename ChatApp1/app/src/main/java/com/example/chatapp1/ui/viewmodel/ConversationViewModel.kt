package com.example.chatapp1.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp1.Conversation
import com.example.chatapp1.MessageWithReceiverAndConversationInfo
import com.example.chatapp1.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
@OptIn(DelicateCoroutinesApi::class)
class ConversationViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {
    private val _conversationWithUsersAndLatestMessage =
        MutableLiveData<List<MessageWithReceiverAndConversationInfo>>()
    val conversationWithUsersAndLatestMessage: LiveData<List<MessageWithReceiverAndConversationInfo>> =
        _conversationWithUsersAndLatestMessage

    suspend fun insertConversation(conversation: Conversation): Boolean {
        var result: Long
        withContext(Dispatchers.IO) {
            result = appRepository.insertConversation(conversation)
        }
        return result != -1L
    }

    suspend fun updateConversation(conversation: Conversation): Boolean {
        var result: Int
        withContext(Dispatchers.IO) {
            result = appRepository.updateConversation(conversation)
        }
        return result > 0
    }

    suspend fun deleteConversation(conversation: Conversation): Boolean {
        var result: Int
        withContext(Dispatchers.IO) {
            result = appRepository.deleteConversation(conversation)
        }
        return result > 0
    }

    fun getMessageWithReceiverAndConversationInfo(userId: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            val conversations =
                appRepository.getLatestMessagesWithReceiverAndConversationInfo(userId)
            _conversationWithUsersAndLatestMessage.postValue(conversations)
        }
    }

    fun getConversation(userId1: Int, userId2: Int): LiveData<Conversation> {
        val result = MutableLiveData<Conversation>()
        GlobalScope.launch(Dispatchers.IO) {
            val conversation = appRepository.getConversation(userId1, userId2)
            result.postValue(conversation)
        }
        return result
    }
}