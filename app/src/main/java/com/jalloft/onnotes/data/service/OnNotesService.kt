package com.jalloft.onnotes.data.service

import com.jalloft.onnotes.common.RequestResponse
import com.jalloft.onnotes.data.dto.auth.AuthResponse
import com.jalloft.onnotes.data.dto.auth.SignInRequest
import com.jalloft.onnotes.data.dto.auth.SignUpRequest
import com.jalloft.onnotes.data.dto.note.NoteRequest
import com.jalloft.onnotes.data.dto.note.NoteResponse
import com.jalloft.onnotes.data.dto.note.UpdateDeleteNoteResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface OnNotesService {

    @POST("signin")
    suspend fun signIn(@Body signInRequest: SignInRequest): Response<AuthResponse>

    @POST("signup")
    suspend fun signUp(@Body siginUpRequest: SignUpRequest): Response<AuthResponse>

    @GET("notes")
    suspend fun getNotes(@Header("Authorization") token: String,): Response<List<NoteResponse>>

    @GET("note/{id}")
    suspend fun getNote(@Header("Authorization") token: String, @Path("id") noteId: String): Response<NoteResponse>

    @DELETE("note/{id}")
    suspend fun deleteNote(@Header("Authorization") token: String, @Path("id") noteId: String): Response<UpdateDeleteNoteResponse>

    @POST("note")
    suspend fun addNote(@Header("Authorization") token: String, @Body noteRequest: NoteRequest): Response<NoteResponse>

    @PUT("note/{id}")
    suspend fun updateNote(@Header("Authorization") token: String, @Path("id") noteId: String, @Body noteRequest: NoteRequest): Response<UpdateDeleteNoteResponse>

}