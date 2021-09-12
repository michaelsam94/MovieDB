package com.example.moviedb.di

import com.example.moviedb.data.MovieDataSource
import com.example.moviedb.data.network.MovieRemoteDataSourceImp
import dagger.Binds
import dagger.Module

@Module
abstract class MovieDataSourceModule {

    @Binds
    abstract fun provideMovieRemoteDataSource(remote: MovieRemoteDataSourceImp): MovieDataSource

}