package com.example.tinkoffapp.ui

import com.example.tinkoffapp.data.PostResult

interface IRepository<out T> {
    suspend fun getData(type: String, page: Int): PostResult<T>
}