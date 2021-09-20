package com.example.moviedb.data

import com.example.moviedb.ui.model.Movie
import io.reactivex.rxjava3.core.Observable

interface MovieRepository {

    fun getMoviesCallback(repoCallback: RepoCallBack<List<Movie>>)

    suspend fun getMoviesCoroutine() : Result<List<Movie>>

    fun getMoviesRx(): Observable<List<Movie>>
}