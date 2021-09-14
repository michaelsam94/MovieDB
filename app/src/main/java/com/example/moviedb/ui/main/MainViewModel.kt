package com.example.moviedb.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.data.MovieRepository
import com.example.moviedb.data.MovieRepositoryImp
import com.example.moviedb.data.RepoCallBack
import com.example.moviedb.data.Result
import com.example.moviedb.ui.model.Movie
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainViewModel @Inject constructor(private val repo: MovieRepository) : ViewModel(){


    private val _mainState = MutableLiveData<MainViewState>()
    val mainViewState: LiveData<MainViewState> = _mainState

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

    fun getMoviesCoroutine(){
        _mainState.value = Progress
        viewModelScope.launch {
            val res = repo.getMoviesCoroutine()
            if(res is Result.Success) {
                _mainState.value = Success(res.data)
            } else {
                _mainState.value = Error((res as Result.Error).exception.message)
            }
        }
    }

}