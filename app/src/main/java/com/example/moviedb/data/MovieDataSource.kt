package com.example.moviedb.data

import com.example.moviedb.NowPlayingRes
import com.example.moviedb.data.network.NetworkCallback

interface MovieDataSource {
    fun getMovies(callback: NetworkCallback<NowPlayingRes>)
}