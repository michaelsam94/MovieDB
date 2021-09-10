package com.example.moviedb.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.moviedb.any
import com.example.moviedb.data.MovieRepository
import com.example.moviedb.data.RepoCallBack
import com.example.moviedb.ui.main.*
import com.example.moviedb.ui.model.Movie
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.whenever


class MainViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var repo: MovieRepository
    private lateinit var callback: RepoCallBack<List<Movie>>

    @Before
    fun setup() {
        repo = mock(MovieRepository::class.java)
        viewModel = MainViewModel(repo)
        callback = object : RepoCallBack<List<Movie>> {
            override fun onSuccess(data: List<Movie>?) {
            }

            override fun onError(error: String?) {
            }
        }
    }

    @Test
    fun `Get Movies shows Progress`() {
        viewModel.getMovies()
        Assert.assertEquals(Progress, viewModel.mainViewState.value)
    }

    @Test
    fun `Get Movies returns Empty Result`() {
        //given
        doAnswer {
            val callBack: RepoCallBack<List<Movie>> = it.arguments[0] as RepoCallBack<List<Movie>>
            callBack.onSuccess(emptyList())
        }.whenever(repo).getMovies(any(RepoCallBack::class))
        //then
        val observer: Observer<MainViewState> = Observer {
            Assert.assertEquals(Success<List<Movie>>(emptyList()),it)
        }
        viewModel.mainViewState.observeForever(observer)
        //when
        repo.getMovies(callback)
    }

    @Test
    fun `Get Movies returns Not Empty Result`() {
        doAnswer {
            val callBack: RepoCallBack<List<Movie>> = it.arguments[0] as RepoCallBack<List<Movie>>
            callBack.onSuccess(listOf(Movie("title","picture","overview")))
        }.whenever(repo).getMovies(any(RepoCallBack::class))
        val observer: Observer<MainViewState> = Observer {
            Assert.assertEquals(Success(listOf(Movie("title","picture","overview"))),it)
        }

        viewModel.mainViewState.observeForever(observer)
        repo.getMovies(callback)
    }


    @Test
    fun `Get Movies returns Error`() {
        doAnswer {
            val callBack: RepoCallBack<List<Movie>> = it.arguments[0] as RepoCallBack<List<Movie>>
            callBack.onError("error")
        }.whenever(repo).getMovies(any(RepoCallBack::class))
        val observer: Observer<MainViewState> = Observer {
            Assert.assertEquals(Error("error"),it)
        }

        viewModel.mainViewState.observeForever(observer)
        repo.getMovies(callback)
    }

    @Test
    fun `Get Movies returns success with null`() {
        doAnswer {
            val callBack: RepoCallBack<List<Movie>> = it.arguments[0] as RepoCallBack<List<Movie>>
            callBack.onSuccess(null)
        }.whenever(repo).getMovies(any(RepoCallBack::class))
        val observer: Observer<MainViewState> = Observer {
            Assert.assertEquals(Success(null),it)
        }

        viewModel.mainViewState.observeForever(observer)
        repo.getMovies(callback)
    }

    @Test
    fun `Get Movies returns error with null`() {
        doAnswer {
            val callBack: RepoCallBack<List<Movie>> = it.arguments[0] as RepoCallBack<List<Movie>>
            callBack.onError(null)
        }.whenever(repo).getMovies(any(RepoCallBack::class))
        val observer: Observer<MainViewState> = Observer {
            Assert.assertEquals(Error(null),it)
        }
        viewModel.mainViewState.observeForever(observer)
        repo.getMovies(callback)
    }



}