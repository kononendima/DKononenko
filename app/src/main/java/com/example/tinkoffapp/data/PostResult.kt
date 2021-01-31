package com.example.tinkoffapp.data

sealed class PostResult<out T> {
    data class Success<T : Any>(val items: T) : PostResult<T>()
    data class Error(val error: Exception) : PostResult<Nothing>()
}