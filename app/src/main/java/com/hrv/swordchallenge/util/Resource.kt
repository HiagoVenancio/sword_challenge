package com.hrv.swordchallenge.util

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Message<T>(code: T?, message: String) : Resource<T>(code, message)
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}