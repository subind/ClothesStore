package com.example.clothesstore

import android.app.Application
import com.example.clothesstore.di.AppComponent
import com.example.clothesstore.di.DaggerAppComponent

class MyApplication: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}