package com.example.moviedb.data

import com.example.moviedb.NowPlayingRes
import com.example.moviedb.data.network.NetworkCallback
import io.reactivex.rxjava3.core.Observable

interface MovieDataSource {
    fun getMovies(callback: NetworkCallback<NowPlayingRes>)

    suspend fun getMoviesCoroutine() : Result<NowPlayingRes>

    fun getMoviesRx(): Observable<NowPlayingRes>
}