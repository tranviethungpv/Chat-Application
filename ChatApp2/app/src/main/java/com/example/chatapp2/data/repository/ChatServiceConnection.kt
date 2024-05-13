package com.example.chatapp2.data.repository

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.chatapp1.IChatService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ChatServiceConnection @Inject constructor(
    @ApplicationContext private val context: Context
) : ServiceConnection {
    private var service: IChatService? = null
    val serviceConnected = MutableLiveData<Boolean>()

    init {
        connect()
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        Log.d("ChatServiceConnection", "onServiceConnected: $service")
        this.service = IChatService.Stub.asInterface(service)
        serviceConnected.value = true
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        service = null
    }

    fun getChatService(): IChatService? = service

    private fun connect() {
        context.bindService(
            Intent().apply {
                setClassName(
                    "com.example.chatapp1",
                    "com.example.chatapp1.service.ChatService"
                )
            }, this, Context.BIND_AUTO_CREATE
        )
    }
}