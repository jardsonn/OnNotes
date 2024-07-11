package com.jalloft.onnotes.data.dto.note

import kotlinx.serialization.Serializable

/**
 * Created by Jardson Costa on 16/06/2024.
 */
@Serializable
data class NoteRequest(
    val title: String?,
    val content: String?,
)
