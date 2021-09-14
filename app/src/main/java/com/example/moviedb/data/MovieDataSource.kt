package com.example.moviedb.data

import com.example.moviedb.NowPlayingRes
import com.example.moviedb.data.network.NetworkCallback
import com.example.moviedb.ui.model.Movie

interface MovieDataSource {
    fun getMovies(callback: NetworkCallback<NowPlayingRes>)

    suspend fun getMoviesCoroutine() : Result<NowPlayingRes>
}