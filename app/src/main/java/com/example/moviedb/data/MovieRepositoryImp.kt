package com.example.moviedb.data

import com.example.moviedb.NowPlayingRes
import com.example.moviedb.data.network.NetworkCallback
import com.example.moviedb.data.network.POSTER_PREFIX
import com.example.moviedb.ui.model.Movie
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(private val movieRemoteDataSource: MovieDataSource): MovieRepository {

    override fun getMovies(repoCallback: RepoCallBack<List<Movie>>){
        movieRemoteDataSource.getMovies(object : NetworkCallback<NowPlayingRes> {
            override fun onSuccess(data: NowPlayingRes?) {
                repoCallback.onSuccess(convertMovieRes(data!!))
            }

            override fun onError(error: String?) {
                repoCallback.onError(error)
            }

        })
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