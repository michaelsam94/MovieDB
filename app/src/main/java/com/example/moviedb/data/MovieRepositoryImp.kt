package com.example.moviedb.data

import com.example.moviedb.NowPlayingRes
import com.example.moviedb.data.network.NetworkCallback
import com.example.moviedb.data.network.POSTER_PREFIX
import com.example.moviedb.ui.model.Movie
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(private val movieRemoteDataSource: MovieDataSource): MovieRepository {

    override fun getMoviesCallback(repoCallback: RepoCallBack<List<Movie>>){
        movieRemoteDataSource.getMovies(object : NetworkCallback<NowPlayingRes> {
            override fun onSuccess(data: NowPlayingRes?) {
                repoCallback.onSuccess(convertMovieRes(data!!))
            }

            override fun onError(error: String?) {
                repoCallback.onError(error)
            }

        })
    }



    override suspend fun getMoviesCoroutine(): Result<List<Movie>> {
        val res = movieRemoteDataSource.getMoviesCoroutine()
        if(res is Result.Success){
            val resConverted = convertMovieRes(res.data)
            return Result.Success(resConverted)
        }
        return Result.Error((res as Result.Error).exception)
    }

    override fun getMoviesRx(): Observable<List<Movie>> {
        return movieRemoteDataSource.getMoviesRx().map { convertMovieRes(it) }
    }


    fun convertMovieRes(nowPlayingRes: NowPlayingRes): List<Movie> {
        val result: MutableList<Movie> = mutableListOf()
        nowPlayingRes.results.forEach {
            val movie = Movie(it.title,"$POSTER_PREFIX${it.poster_path}",it.overview)
            result.add(movie)
        }
        return result
    }



}