package com.example.chatapp1.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.chatapp1.Message
import com.example.chatapp1.MessageWithUsersAndConversation
import com.example.chatapp1.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {
    private var _messageWithUsersAndConversation = MutableLiveData<List<MessageWithUsersAndConversation>>()
    val messageWithUsersAndConversation get() = _messageWithUsersAndConversation

    suspend fun insertMessage(message: Message): Boolean {
        var result: Long
        withContext(Dispatchers.IO) {
            result = appRepository.insertMessage(message)
        }
        return result != -1L
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getMessagesWithUsersAndConversation(
        conversationId: Int
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            _messageWithUsersAndConversation.postValue(appRepository.getMessagesWithUsersAndConversation(conversationId))
        }
    }
}