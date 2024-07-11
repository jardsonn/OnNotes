package com.jalloft.onnotes.ui.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jalloft.onnotes.common.RequestResponse
import com.jalloft.onnotes.common.SessionPrefs
import com.jalloft.onnotes.data.Note
import com.jalloft.onnotes.data.dto.note.NoteRequest
import com.jalloft.onnotes.repositories.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID


class HomeViewModel(private val repo: NotesRepository, private val sessionPrefs: SessionPrefs) :
    ViewModel() {
    /*
     getNotes() = remoteDataSource.getNotes()
 getNote(noteId: UUID) = remoteDataSource.getNote(noteId.toSt
 deleteNote(noteId: UUID) = remoteDataSource.deleteNote(noteI
 addNote(noteRequest: NoteRequest) = remoteDataSource.addNote
 updateNote()
     */

    private var _loadingNotesRequest: MutableState<Boolean> = mutableStateOf(false)
    val loadingNotesRequest: Boolean get() = _loadingNotesRequest.value

    private var _errorNotesRequest: MutableState<Int?> = mutableStateOf(0)
    val errorNotesRequest: Int? get() = _errorNotesRequest.value

    private val _notes = MutableStateFlow<List<Note>>(listOf())
    val notes: StateFlow<List<Note>> get() = _notes

    init {
        getNote()
    }

    fun getNote(showLoading: Boolean = true) {
        viewModelScope.launch {
            val token = sessionPrefs.getToken() ?: ""
            _loadingNotesRequest.value = showLoading
            repo.getNotes(token).flowOn(Dispatchers.IO).collectLatest { response ->
                when (response) {
                    RequestResponse.Loading -> {
                        if (showLoading) {
                            _loadingNotesRequest.value = true
                            _errorNotesRequest.value = null
                        }
                        Timber.i("getNote.Loading")
                    }

                    is RequestResponse.Success -> {
                        if (showLoading) {
                            _loadingNotesRequest.value = false
                            _errorNotesRequest.value = null
                        }
                        val data = response.data.map {
                            Note(
                                UUID.fromString(it.id),
                                it.title,
                                it.content,
                                it.createdAt
                            )
                        }
                        _notes.value = data
                        Timber.i("getNote.Success = $data")
                    }

                    is RequestResponse.Error -> {
                        if (showLoading) {
                            _loadingNotesRequest.value = false
                            _errorNotesRequest.value = response.code
                        }
                        if (response.code == null) {
                            Timber.i("getNote.Error = ${sessionPrefs.getToken()}")
//                            sessionPrefs.clearSession()
                        }
                        Timber.i("getNote.Error = $response")

                    }
                }
            }
        }
    }
}