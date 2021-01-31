package com.example.tinkoffapp.data.network

import com.example.tinkoffapp.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object ApiFactory {

    @Suppress("EXPERIMENTAL_API_USAGE")
    fun create(): Api {
        val json = Json { ignoreUnknownKeys = true }
        val okHttpClient: OkHttpClient = createOkHttpClient()
        return createRetrofit(okHttpClient)
            .baseUrl(BuildConfig.URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(Api::class.java)
    }

    private fun createOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(1, TimeUnit.MINUTES)
        return okHttpClient.build()
    }

    private fun createRetrofit(okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .client(okHttpClient)
}