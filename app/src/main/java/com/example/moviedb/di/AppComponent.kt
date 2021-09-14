package com.example.moviedb.di

import android.content.Context
import com.example.moviedb.ui.main.MainActivity
import com.example.moviedb.ui.main.di.MainComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Component(modules = [
    AppModule::class,
    NetworkModule::class,
    MovieRepoModule::class,
    SubComponentsModule::class,
    MovieDataSourceModule::class,
    ViewModelBuilderModule::class,
])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun mainComponent(): MainComponent.Factory

}

@Module(
    subcomponents = [
        MainComponent::class
    ]
)
object SubComponentsModule