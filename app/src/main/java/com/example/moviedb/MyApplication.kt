package com.example.moviedb

import android.app.Application
import com.example.moviedb.di.AppComponent
import com.example.moviedb.di.DaggerAppComponent
import com.example.moviedb.di.dataModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
//        startKoin {
//            androidContext(this@MyApplication)
//            androidLogger()
//            modules(listOf(dataModules))
//        }
    }
}