package com.jalloft.onnotes.data.dto.note

import kotlinx.serialization.Serializable
import java.util.UUID

/**
 * Created by Jardson Costa on 16/06/2024.
 */
@Serializable
data class NoteResponse(
    val id: String?,
    val title: String?,
    val content: String?,
    val createdAt: String?
)
