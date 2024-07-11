package com.jalloft.onnotes.repositories

import com.jalloft.onnotes.data.dto.auth.SignInRequest
import com.jalloft.onnotes.data.dto.auth.SignUpRequest
import com.jalloft.onnotes.data.service.OnNotesDataSource


class AuthRepositoryImpl(private val remoteDataSource: OnNotesDataSource) : AuthRepository {

    override suspend fun signUp(signUpRequest: SignUpRequest) =
        remoteDataSource.signUp(signUpRequest)

    override suspend fun signIn(siginInRequest: SignInRequest) =
        remoteDataSource.signIn(siginInRequest)

}