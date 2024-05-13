package com.example.chatapp1.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp1.User
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
class UserViewModel @Inject constructor(
    private val appRepository: AppRepository,
) : ViewModel() {
    private var _users = MutableLiveData<List<User>>()
    val users get() = _users

    suspend fun insertUser(user: User): Boolean {
        var result: Long
        withContext(Dispatchers.IO) {
            result = appRepository.insertUser(user)
        }
        return result != -1L
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