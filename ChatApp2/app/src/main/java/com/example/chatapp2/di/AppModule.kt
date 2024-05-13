package com.example.chatapp2.di

import android.content.Context
import com.example.chatapp2.data.repository.AppRepository
import com.example.chatapp2.data.repository.AppRepositoryImpl
import com.example.chatapp2.utils.SessionManager
import com.example.chatapp2.data.repository.ChatServiceConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideChatServiceConnection(@ApplicationContext context: Context): ChatServiceConnection {
        return ChatServiceConnection(context)
    }

    @Provides
    @Singleton
    fun provideAppRepository(
        chatServiceConnection: ChatServiceConnection
    ): AppRepository = AppRepositoryImpl(chatServiceConnection)

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManager(context)
    }
}