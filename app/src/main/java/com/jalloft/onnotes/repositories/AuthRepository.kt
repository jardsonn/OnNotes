package com.jalloft.onnotes.repositories

import com.jalloft.onnotes.common.RequestResponse
import com.jalloft.onnotes.data.dto.auth.AuthResponse
import com.jalloft.onnotes.data.dto.auth.SignInRequest
import com.jalloft.onnotes.data.dto.auth.SignUpRequest
import kotlinx.coroutines.flow.Flow


interface AuthRepository {

    suspend fun signUp(signUpRequest: SignUpRequest): Flow<RequestResponse<AuthResponse>>
    suspend fun signIn(siginInRequest: SignInRequest): Flow<RequestResponse<AuthResponse>>

}