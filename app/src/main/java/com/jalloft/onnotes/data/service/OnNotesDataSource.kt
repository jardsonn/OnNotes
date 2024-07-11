package com.jalloft.onnotes.data.service

import com.jalloft.onnotes.data.dto.auth.SignInRequest
import com.jalloft.onnotes.data.dto.auth.SignUpRequest
import com.jalloft.onnotes.data.dto.note.NoteRequest


class OnNotesDataSource(private val service: OnNotesService) : BaseDataSource() {

    suspend fun signIn(signInRequest: SignInRequest) = getResult {
        service.signIn(signInRequest)
    }


    suspend fun signUp(signUpRequest: SignUpRequest) = getResult {
        service.signUp(signUpRequest)
    }

    suspend fun getNotes(token: String) = getResult { service.getNotes(token) }

    suspend fun getNote(token: String, noteId: String) = getResult { service.getNote(token, noteId) }

    suspend fun addNote(token: String, noteRequest: NoteRequest) = getResult { service.addNote(token, noteRequest) }

    suspend fun deleteNote(token: String, noteId: String) = getResult { service.deleteNote(token, noteId) }

    suspend fun updateNote(token: String, noteId: String, noteRequest: NoteRequest) =
        getResult { service.updateNote(token, noteId, noteRequest) }

}