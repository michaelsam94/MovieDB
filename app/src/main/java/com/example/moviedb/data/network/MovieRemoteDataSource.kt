package com.example.moviedb.data.network

import com.example.moviedb.NowPlayingRes
import com.example.moviedb.data.NetworkCallback
import com.example.moviedb.helper.Retrofit

const val BASE_URL = "https://api.themoviedb.org/3/"
const val API_KEY = "6bdd92e829e5beb0f2902f834db79e10"
const val POSTER_PREFIX = "https://image.tmdb.org/t/p/w185"

class MovieRemoteDataSource(val movieService: MovieService) {

    fun getMovies(callback: NetworkCallback<NowPlayingRes>) {
        Retrofit.doCall(movieService.getNowPlaying(),callback)
    }

}