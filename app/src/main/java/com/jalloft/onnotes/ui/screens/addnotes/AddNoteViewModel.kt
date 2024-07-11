package com.jalloft.onnotes.ui.screens.addnotes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jalloft.onnotes.common.RequestResponse
import com.jalloft.onnotes.common.SessionPrefs
import com.jalloft.onnotes.data.dto.note.NoteRequest
import com.jalloft.onnotes.repositories.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import timber.log.Timber


class AddNoteViewModel(private val repo: NotesRepository, private val sessionPrefs: SessionPrefs) :
    ViewModel() {

    private var _errorAddNewNoteRequest: MutableState<Int?> = mutableStateOf(0)
    val errorAddNewNoteRequest: Int? get() = _errorAddNewNoteRequest.value

    private var _isLoadingAddNewNote: MutableState<Boolean> = mutableStateOf(false)
    val isLoadingAddNewNote: Boolean get() = _isLoadingAddNewNote.value


    private var _isSuccessAddNewNote: MutableState<Boolean?> = mutableStateOf(null)
    val isSuccessAddNewNote: Boolean? get() = _isSuccessAddNewNote.value


    fun addNote(noteRequest: NoteRequest) {
        viewModelScope.launch {
            val token = sessionPrefs.getToken() ?: ""
            _isLoadingAddNewNote.value = true
            repo.addNote(token, noteRequest).flowOn(Dispatchers.IO).collectLatest { response ->
                when (response) {
                    RequestResponse.Loading -> {
                        _isLoadingAddNewNote.value = true
                        _errorAddNewNoteRequest.value = null
                        _isSuccessAddNewNote.value = false
                        Timber.i("addNote.Loading")
                    }

                    is RequestResponse.Success -> {
                        _isLoadingAddNewNote.value = false
                        _isSuccessAddNewNote.value = true
                        _errorAddNewNoteRequest.value = null
                    }

                    is RequestResponse.Error -> {
                        _isSuccessAddNewNote.value = false
                        _isLoadingAddNewNote.value = false
                        _errorAddNewNoteRequest.value = response.code
                        if (response.code == null) {
                            Timber.i("addNote.Error = ${sessionPrefs.getToken()}")
//                            sessionPrefs.clearSession()
                        }
                        Timber.i("addNote.Error = $response")

                    }
                }
            }
        }
    }
}