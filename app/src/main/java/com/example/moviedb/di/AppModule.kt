package com.example.moviedb.di

import com.example.moviedb.data.MovieRepositoryImp
import com.example.moviedb.data.network.MovieRemoteDataSourceImp
import com.example.moviedb.data.network.MovieService
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
object AppModule {

    @JvmStatic
    @Provides
    fun provideMovieRemotedDataSourceImp(
        api: MovieService,
        ioDispatcher: CoroutineDispatcher
    ): MovieRemoteDataSourceImp {
        return MovieRemoteDataSourceImp(
            api, ioDispatcher
        )
    }

    @JvmStatic
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

}