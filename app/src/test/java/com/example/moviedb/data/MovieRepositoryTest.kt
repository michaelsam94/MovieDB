package com.example.moviedb.data

import com.example.moviedb.NowPlayingRes
import com.example.moviedb.data.network.NetworkCallback
import com.example.moviedb.ui.model.Movie
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class MovieRepositoryTest {

    private lateinit var movieRepository: MovieRepositoryImp
    private lateinit var movieRemoteDataSource: FakeMovieRemoteDataSource

    @Before
    fun createRepository() {
        movieRemoteDataSource = FakeMovieRemoteDataSource()
        movieRepository = MovieRepositoryImp(movieRemoteDataSource)
    }


    @Test
    fun `get movies return list of movies`(){
        movieRepository.getMovies(object : RepoCallBack<List<Movie>>{
            override fun onSuccess(moviesList: List<Movie>?) {
                movieRemoteDataSource.getMovies(object : NetworkCallback<NowPlayingRes>{
                    override fun onSuccess(data: NowPlayingRes?) {
                        val movies = movieRepository.convertMovieRes(data!!)
                        assertThat(movies).isEqualTo(moviesList)
                    }

                    override fun onError(error: String?) {

                    }

                })
            }

            override fun onError(error: String?) {

            }

        })
    }




}