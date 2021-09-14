package com.example.moviedb.data.network

import com.example.moviedb.NowPlayingRes
import com.example.moviedb.data.MovieDataSource
import com.example.moviedb.data.Result
import com.example.moviedb.helper.Retrofit
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

const val BASE_URL = "https://api.themoviedb.org/3/"
const val API_KEY = "6bdd92e829e5beb0f2902f834db79e10"
const val POSTER_PREFIX = "https://image.tmdb.org/t/p/w185"

class MovieRemoteDataSourceImp @Inject constructor(
    val movieService: MovieService,
    val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    MovieDataSource {

    override fun getMovies(callback: NetworkCallback<NowPlayingRes>) {
        Retrofit.doCall(movieService.getNowPlaying(), callback)
    }

    override suspend fun getMoviesCoroutine(): Result<NowPlayingRes> = withContext(ioDispatcher) {
        return@withContext try{
            Result.Success(movieService.getNowPlayingCoroutine())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}