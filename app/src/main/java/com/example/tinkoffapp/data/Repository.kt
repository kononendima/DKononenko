package com.example.tinkoffapp.data

import com.example.tinkoffapp.data.network.Api
import com.example.tinkoffapp.ui.IRepository

class Repository(private val api: Api) : IRepository<List<Model>> {
    override suspend fun getData(type: String, page: Int): PostResult<List<Model>> {
        return try {
            val data = api.getData(type, page)
            PostResult.Success(data.result)
        } catch (e: Exception) {
            PostResult.Error(e)
        }
    }
}