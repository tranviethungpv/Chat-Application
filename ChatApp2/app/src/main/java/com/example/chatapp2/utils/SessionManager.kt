package com.example.chatapp2.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.chatapp1.User
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(context: Context) {

    companion object {
        private const val PREF_NAME = "com.example.chatapp1.PREF_NAME"
        private const val IS_LOGGED_IN = "IS_LOGGED_IN"
        private const val USER_INFO = "USER_INFO"
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveLoginSession(user: User) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(IS_LOGGED_IN, true)
        editor.putString(USER_INFO, Gson().toJson(user))
        editor.apply()
    }

    fun clearLoginSession() {
        val editor = sharedPreferences.edit()
        editor.putBoolean(IS_LOGGED_IN, false)
        editor.remove(USER_INFO)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false)
    }

    fun getUserInfo(): User? {
        val userInfo = sharedPreferences.getString(USER_INFO, null)
        return userInfo?.let { Gson().fromJson(it, User::class.java) }
    }
}