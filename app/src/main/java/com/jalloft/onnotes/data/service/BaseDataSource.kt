package com.jalloft.onnotes.data.service


import com.google.gson.Gson
import com.jalloft.onnotes.common.RequestResponse
import com.jalloft.onnotes.data.error.AuthenticationErrorCodes.GENERIC_ERROR
import com.jalloft.onnotes.data.error.ErrorResponse
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import timber.log.Timber


abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>) = flow<RequestResponse<T>> {
        try {
            emit(RequestResponse.Loading)
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(RequestResponse.Success(body))
                } else {
                    emit(RequestResponse.Error(GENERIC_ERROR))
                }
            } else {
                val errorResponse = response
                    .errorBody()
                    ?.string()?.let { error ->
                        Gson().fromJson(error, ErrorResponse::class.java)
                    }
                emit(RequestResponse.Error(errorResponse?.code))
                Timber.e("A chamada de rede falhou pelo seguinte motivo: ${response.message()}\n${errorResponse?.message}")
            }
        } catch (e: Exception) {
            emit(RequestResponse.Error(GENERIC_ERROR))
        }
    }

}