package com.example.tinkoffapp.ui.view_states

import com.example.tinkoffapp.data.Model

data class ViewState(
    val isNext: Boolean = false,
    val isPrev: Boolean = false,
    val isReload: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val isEmpty: Boolean = false,
    val items: List<Model> = listOf()
)