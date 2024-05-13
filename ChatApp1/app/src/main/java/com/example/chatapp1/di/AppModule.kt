package com.example.chatapp1.di

import android.content.Context
import androidx.room.Room
import com.example.chatapp1.data.dao.AppDao
import com.example.chatapp1.data.dao.MessageDao
import com.example.chatapp1.data.dao.UserDao
import com.example.chatapp1.data.dao.ConversationDao
import com.example.chatapp1.data.database.AppDatabase
import com.example.chatapp1.data.repository.AppRepository
import com.example.chatapp1.data.repository.AppRepositoryImpl
import com.example.chatapp1.utils.SessionManager
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
    fun provideDatabase(@ApplicationContext context: Context) : AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "chat_database"
        ).createFromAsset("chat_database.db").build()
    }

    @Provides
    @Singleton
    fun provideConversationDao(database: AppDatabase) = database.conversationDao()

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase) = database.userDao()

    @Provides
    @Singleton
    fun provideMessageDao(database: AppDatabase) = database.messageDao()

    @Provides
    @Singleton
    fun provideAppDao(database: AppDatabase) = database.appDao()

    @Provides
    @Singleton
    fun provideAppRepository(
        conversationDao: ConversationDao,
        messageDao: MessageDao,
        userDao: UserDao,
        appDao: AppDao
    ) : AppRepository = AppRepositoryImpl(conversationDao, messageDao, userDao, appDao)

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManager(context)
    }
}