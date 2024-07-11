package com.jalloft.onnotes.repositories

import com.jalloft.onnotes.data.dto.note.NoteRequest
import com.jalloft.onnotes.data.service.OnNotesDataSource
import java.util.UUID


class NotesRepositoryImpl(private val remoteDataSource: OnNotesDataSource) : NotesRepository {

    override suspend fun getNotes(token: String) = remoteDataSource.getNotes("Bearer $token")

    override suspend fun getNote(token: String, noteId: UUID) =
        remoteDataSource.getNote("Bearer $token", noteId.toString())

    override suspend fun deleteNote(token: String, noteId: UUID) =
        remoteDataSource.deleteNote("Bearer $token", noteId.toString())

    override suspend fun addNote(token: String, noteRequest: NoteRequest) =
        remoteDataSource.addNote("Bearer $token", noteRequest)

    override suspend fun updateNote(
        token: String,
        noteId: UUID,
        noteRequest: NoteRequest
    ) = remoteDataSource.updateNote("Bearer $token", noteId.toString(), noteRequest)


}