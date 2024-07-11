package com.jalloft.onnotes.common


sealed class RequestResponse<out T> {
    data object Loading: RequestResponse<Nothing>()
    data class Success<T>(val data: T) : RequestResponse<T>()
    data class Error(val code: Int?) : RequestResponse<Nothing>()
}