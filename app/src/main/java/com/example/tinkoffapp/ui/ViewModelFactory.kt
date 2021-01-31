package com.example.tinkoffapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tinkoffapp.data.Repository
import com.example.tinkoffapp.ui.hot_fragment.FragmentViewModel

object ViewModelFactory : ViewModelProvider.Factory {
    private lateinit var repository: Repository

    fun inject(mRepository: Repository) {
        repository = mRepository
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            FragmentViewModel::class.java.isAssignableFrom(modelClass) -> {
                return modelClass.getConstructor(IRepository::class.java).newInstance(repository)
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}