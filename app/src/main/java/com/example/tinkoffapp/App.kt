package com.example.tinkoffapp

import android.app.Application
import com.example.tinkoffapp.data.Repository
import com.example.tinkoffapp.data.network.ApiFactory
import com.example.tinkoffapp.ui.ViewModelFactory

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ViewModelFactory.inject(Repository(ApiFactory.create()))
    }
}