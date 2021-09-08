package com.example.moviedb.di

import com.example.moviedb.data.MovieRepository
import com.example.moviedb.data.MovieRepositoryImp
import dagger.Binds
import dagger.Module

@Module
abstract class MovieRepoModule {
    @Binds
    abstract fun provideMovieRepo(repo: MovieRepositoryImp): MovieRepository
}