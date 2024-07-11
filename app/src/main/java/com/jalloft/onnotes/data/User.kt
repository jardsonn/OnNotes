package com.jalloft.onnotes.data

import com.jalloft.onnotes.data.dto.auth.UserResponse
import kotlinx.serialization.Serializable

/**
 * Created by Jardson Costa on 31/05/2024.
 */
@Serializable
data class User(
    val id: String?,
    val name: String?,
    val email: String?,
)

fun UserResponse.toUser() = User(id, name, email)
