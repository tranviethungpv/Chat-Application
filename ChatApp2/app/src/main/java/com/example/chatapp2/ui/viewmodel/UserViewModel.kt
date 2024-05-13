package com.example.chatapp2.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp1.User
import com.example.chatapp2.data.repository.AppRepository
import com.example.chatapp2.data.repository.ChatServiceConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
@OptIn(DelicateCoroutinesApi::class)
class UserViewModel @Inject constructor(
    private val appRepository: AppRepository,
    chatServiceConnection: ChatServiceConnection
) : ViewModel() {
    private var _users = MutableLiveData<List<User>>()
    val users get() = _users

    init {
        chatServiceConnection.serviceConnected.observeForever {
            if (it) {
                getUsers()
                Log.d("UserViewModel", "Service connected")
            }
        }
    }

    suspend fun insertUser(user: User): Boolean {
        var result: Long
        withContext(Dispatchers.IO) {
            result = appRepository.insertUser(user)
        }
        return result != -1L
    }

    suspend fun updateUser(user: User): Boolean {
        var result: Int
        withContext(Dispatchers.IO) {
            result = appRepository.updateUser(user)
        }
        return result > 0
    }

    suspend fun deleteUser(user: User): Boolean {
        var result: Int
        withContext(Dispatchers.IO) {
            result = appRepository.deleteUser(user)
        }
        return result > 0
    }

    fun getUsers() {
        GlobalScope.launch(Dispatchers.IO) {
            _users.postValue(appRepository.getAllUsers())
        }
    }

    fun getUserById(userId: Int): LiveData<User> {
        val result = MutableLiveData<User>()
        GlobalScope.launch(Dispatchers.IO) {
            result.postValue(appRepository.getUserById(userId))
        }
        return result
    }
}