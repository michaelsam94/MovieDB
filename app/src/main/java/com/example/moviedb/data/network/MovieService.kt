package com.example.moviedb.data.network

import com.example.moviedb.NowPlayingRes
import retrofit2.Call
import retrofit2.http.GET

interface MovieService {

    @GET("movie/now_playing")
    fun getNowPlaying() : Call<NowPlayingRes>
}