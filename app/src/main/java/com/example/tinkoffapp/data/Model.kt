package com.example.tinkoffapp.data

import kotlinx.serialization.Serializable

@Serializable
data class ModelResp(val result: List<Model>)

@Serializable
data class Model(val id: Int, val description: String, val gifURL: String)