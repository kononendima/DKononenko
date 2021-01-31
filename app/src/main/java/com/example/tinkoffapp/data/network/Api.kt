package com.example.tinkoffapp.data.network

import com.example.tinkoffapp.data.ModelResp
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("{type}/{page}?json=true")
    suspend fun getData(@Path("type") type: String, @Path("page") page: Int): ModelResp
}