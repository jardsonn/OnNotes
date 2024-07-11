package com.jalloft.onnotes.common

import com.jalloft.onnotes.data.User


interface SessionPrefs {
    fun saveUser(user: User)

    fun getUser(): User?

    fun saveToken(token: String)

    fun getToken(): String?

    fun clearSession()

}