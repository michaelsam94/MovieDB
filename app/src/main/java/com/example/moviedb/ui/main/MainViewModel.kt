package com.example.moviedb.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviedb.data.MovieRepository
import com.example.moviedb.data.MovieRepositoryImp
import com.example.moviedb.data.RepoCallBack
import com.example.moviedb.ui.model.Movie
import javax.inject.Inject


class MainViewModel @Inject constructor(private val repo: MovieRepository){


    private val _mainState = MutableLiveData<MainViewState>()
    val mainViewState: LiveData<MainViewState>
        get() = _mainState

    fun getMovies(){
        _mainState.value = Progress
        repo.getMovies(object : RepoCallBack<List<Movie>> {
            override fun onSuccess(data: List<Movie>?) {
                _mainState.value = Success(data!!)
            }

            override fun onError(error: String?) {
                _mainState.value = Error(error!!)
            }

        })
    }

}