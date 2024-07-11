package com.jalloft.onnotes.repositories

import com.jalloft.onnotes.common.RequestResponse
import com.jalloft.onnotes.data.dto.note.NoteRequest
import com.jalloft.onnotes.data.dto.note.NoteResponse
import com.jalloft.onnotes.data.dto.note.UpdateDeleteNoteResponse
import kotlinx.coroutines.flow.Flow
import java.util.UUID


interface NotesRepository {

    suspend fun getNotes(token: String): Flow<RequestResponse<List<NoteResponse>>>

    suspend fun getNote(token: String, noteId: UUID): Flow<RequestResponse<NoteResponse>>

    suspend fun deleteNote(token: String, noteId: UUID): Flow<RequestResponse<UpdateDeleteNoteResponse>>

    suspend fun addNote(token: String, noteRequest: NoteRequest): Flow<RequestResponse<NoteResponse>>

    suspend fun updateNote(token: String, noteId: UUID, noteRequest: NoteRequest): Flow<RequestResponse<UpdateDeleteNoteResponse>>


}