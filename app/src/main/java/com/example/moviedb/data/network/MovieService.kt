package com.example.moviedb.data.network


import com.example.moviedb.NowPlayingRes
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.GET

interface MovieService {

    @GET("movie/now_playing")
    fun getNowPlaying() : Call<NowPlayingRes>

    @GET("movie/now_playing")
    suspend fun getNowPlayingCoroutine(): NowPlayingRes

    @GET("movie/now_playing")
    fun getNowPlayingRx(): Observable<NowPlayingRes>

}