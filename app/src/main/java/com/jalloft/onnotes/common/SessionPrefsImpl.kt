package com.jalloft.onnotes.common

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jalloft.onnotes.data.User


class SessionPrefsImpl(private val context: Context) : SessionPrefs {

    private val sessionPrefs by lazy {
        context.applicationContext.getSharedPreferences(SESSION_PREFS, Context.MODE_PRIVATE)
    }

    override fun saveUser(user: User) {
        val gson = Gson()
        val type = object : TypeToken<User>() {}.type
        sessionPrefs.edit().putString(PREFS_USER, gson.toJson(user, type)).apply()
    }

    override fun getUser(): User? {
        val gson = Gson()
        val json = sessionPrefs.getString(PREFS_USER, null)
        return gson.fromJson(json, User::class.java)
    }

    override fun saveToken(token: String) {
        sessionPrefs.edit().putString(PREFS_TOKEN, token).apply()
    }

    override fun getToken(): String? {
        return sessionPrefs.getString(PREFS_TOKEN, null)
    }

    override fun clearSession() {
        sessionPrefs.edit().clear().apply()
    }

    companion object {
        const val SESSION_PREFS = "SESSION_PREFS"
        const val PREFS_USER = "USER"
        const val PREFS_TOKEN = "TOKEN"
    }
}