package com.jalloft.onnotes.ui.screens.auth.siginup

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jalloft.onnotes.common.RequestResponse
import com.jalloft.onnotes.common.SessionPrefs
import com.jalloft.onnotes.data.dto.auth.AuthResponse
import com.jalloft.onnotes.data.dto.auth.SignUpRequest
import com.jalloft.onnotes.data.toUser
import com.jalloft.onnotes.repositories.AuthRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber


class SiginUpViewModel(private val repo: AuthRepository, private val sessionPrefs: SessionPrefs) :
    ViewModel() {

    private var _signUpLoading: MutableState<Boolean> = mutableStateOf(false)
    val signUpLoading: Boolean get() = _signUpLoading.value

    private var _signUpErrorCode: MutableState<Int?> = mutableStateOf(null)
    val signUpErrorCode: Int? get() = _signUpErrorCode.value

    private var _signUpAuthSuccess: MutableState<AuthResponse?> = mutableStateOf(null)
    val signUpAuthSuccess: AuthResponse? get() = _signUpAuthSuccess.value


    fun siginUp(signUpRequest: SignUpRequest) {
        viewModelScope.launch {
            _signUpLoading.value = true
            repo.signUp(signUpRequest).collectLatest { response ->
                when (response) {
                    RequestResponse.Loading -> {
                        _signUpLoading.value = true
                        _signUpErrorCode.value = null
                        Timber.i("SignUpState.carregando")
                    }

                    is RequestResponse.Success -> {
                        _signUpLoading.value = false
                        val authResponse = response.data
                        val user = authResponse.userResponse?.toUser()
                        if (user != null) {
                            sessionPrefs.saveUser(user)
                        }
                        authResponse.token?.let { sessionPrefs.saveToken(it) }
                        Timber.i("SignUpState.token = ${authResponse.token}")
                        Timber.i("SignUpState.userResponse = ${authResponse.userResponse}")
                        _signUpAuthSuccess.value = authResponse
                        _signUpErrorCode.value = null
                        Timber.i("SignUpState.sucesso")

                    }

                    is RequestResponse.Error -> {
                        _signUpLoading.value = false
                        _signUpErrorCode.value = response.code
                        Timber.i("SignUpState.erro")
                    }
                }
            }
        }
    }

}