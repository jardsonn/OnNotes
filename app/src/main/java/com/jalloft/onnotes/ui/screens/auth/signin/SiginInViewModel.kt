package com.jalloft.onnotes.ui.screens.auth.signin

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jalloft.onnotes.common.RequestResponse
import com.jalloft.onnotes.common.SessionPrefs
import com.jalloft.onnotes.data.dto.auth.AuthResponse
import com.jalloft.onnotes.data.dto.auth.SignInRequest
import com.jalloft.onnotes.data.toUser
import com.jalloft.onnotes.repositories.AuthRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber


class SiginInViewModel(private val repo: AuthRepository, private val sessionPrefs: SessionPrefs) :
    ViewModel() {


    private var _signInLoading: MutableState<Boolean> = mutableStateOf(false)
    val signInLoading: Boolean get() = _signInLoading.value

    private var _signInErrorCode: MutableState<Int?> = mutableStateOf(null)
    val signInErrorCode: Int? get() = _signInErrorCode.value

    private var _signInAuthSuccess: MutableState<AuthResponse?> = mutableStateOf(null)
    val signInAuthSuccess: AuthResponse? get() = _signInAuthSuccess.value


    fun signIn(signInRequest: SignInRequest){
        viewModelScope.launch {
            _signInLoading.value = true
            repo.signIn(signInRequest).collectLatest { response ->
                when(response){
                    RequestResponse.Loading -> {
                        _signInLoading.value = true
                        _signInErrorCode.value = null
                    }

                    is RequestResponse.Success -> {
                        _signInLoading.value = false
                        val authResponse = response.data
                        val user = authResponse.userResponse?.toUser()
                        if (user != null) {
                            sessionPrefs.saveUser(user)
                        }
                        authResponse.token?.let { sessionPrefs.saveToken(it) }
                        Timber.i("SignInState.token = ${authResponse.token}")
                        Timber.i("SignInState.userResponse = ${authResponse.userResponse}")
                        _signInAuthSuccess.value = authResponse
                        _signInErrorCode.value = null
                    }

                    is RequestResponse.Error -> {
                        _signInLoading.value = false
                        _signInErrorCode.value = response.code
                    }
                }
            }
        }
    }

}