package com.example.moviedb.ui.main.di

import androidx.lifecycle.ViewModel
import com.example.moviedb.di.ViewModelKey
import com.example.moviedb.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindViewModel(viewmodel: MainViewModel): ViewModel

}