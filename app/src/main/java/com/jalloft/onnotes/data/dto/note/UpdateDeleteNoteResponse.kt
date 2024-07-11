package com.jalloft.onnotes.data.dto.note

import kotlinx.serialization.Serializable

/**
 * Created by Jardson Costa on 16/06/2024.
 */
@Serializable
data class UpdateDeleteNoteResponse(
    val code: Int,
    val message: String
)

