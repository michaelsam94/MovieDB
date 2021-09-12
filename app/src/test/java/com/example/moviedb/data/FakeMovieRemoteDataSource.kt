package com.example.moviedb.data

import com.example.moviedb.Dates
import com.example.moviedb.NowPlayingRes
import com.example.moviedb.Result
import com.example.moviedb.data.network.NetworkCallback

class FakeMovieRemoteDataSource : MovieDataSource {

    private lateinit var movieRes: NowPlayingRes
    private var shouldReturnNetworkError = false

    fun initMovies() {
        movieRes = NowPlayingRes(
            dates = Dates(maximum = "",minimum = ""),
            page = 1,
            results = listOf(
                Result(
                    overview = "overview",
                    title = "title",
                    poster_path = "poster_path"
                )
            ),
            total_pages = 20,
            total_results = 2000,
        )
    }

    private fun shouldReturnNetworkError() : Boolean = shouldReturnNetworkError


    override fun getMovies(callback: NetworkCallback<NowPlayingRes>) {
        initMovies()
        if(shouldReturnNetworkError()) {
            callback.onError("error")
        } else {
            callback.onSuccess(movieRes)
        }
    }



}