package com.jalloft.onnotes.data.error

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val code: Int? = null,
    val message: String? = null,
)
