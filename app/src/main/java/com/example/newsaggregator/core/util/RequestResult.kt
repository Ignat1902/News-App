package com.example.newsaggregator.core.util

sealed class RequestResult<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : RequestResult<T>(data)
    class Success<T>(data: T? = null) : RequestResult<T>(data)
    class Error<T>(message: String? = null, data: T? = null) : RequestResult<T>(data, message)
}

/*
fun <T> Result<T>.toRequestResult(): RequestResult<T> {
    return when {
        isSuccess -> RequestResult.Success<T>(getOrNull())
        isFailure -> RequestResult.Error<T>(exceptionOrNull()?.message, getOrNull())
        else -> error("Impossible")
    }
}*/
