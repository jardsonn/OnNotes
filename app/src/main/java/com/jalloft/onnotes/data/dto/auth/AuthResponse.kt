package com.jalloft.onnotes.data.dto.auth



data class AuthResponse(
    val token: String?,
    val userResponse: UserResponse?
)