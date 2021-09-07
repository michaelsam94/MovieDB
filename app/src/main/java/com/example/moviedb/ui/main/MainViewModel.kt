package com.example.moviedb.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviedb.data.MovieRepository
import com.example.moviedb.data.RepoCallBack
import com.example.moviedb.helper.Threading
import com.example.moviedb.ui.model.Movie
import org.koin.core.KoinComponent
import org.koin.core.inject


class MainViewModel(): KoinComponent {

    private val repo:MovieRepository by inject()
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