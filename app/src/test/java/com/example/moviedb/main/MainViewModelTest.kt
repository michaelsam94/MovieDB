package com.example.moviedb.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.moviedb.MainCoroutineRule
import com.example.moviedb.any
import com.example.moviedb.data.MovieRepository
import com.example.moviedb.data.RepoCallBack
import com.example.moviedb.data.Result
import com.example.moviedb.ui.main.*
import com.example.moviedb.ui.model.Movie
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.whenever
import java.lang.Exception


class MainViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

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
        //Assert.assertEquals(Progress, viewModel.mainViewState.value)
        assertThat(viewModel.mainViewState.value).isEqualTo(Progress)
    }

    @Test
    fun `Get Movies returns Empty Result`() {
        //given
        doAnswer {
            val callBack: RepoCallBack<List<Movie>> = it.arguments[0] as RepoCallBack<List<Movie>>
            callBack.onSuccess(emptyList())
        }.whenever(repo).getMoviesCallback(any(RepoCallBack::class))
        //then
        val observer: Observer<MainViewState> = Observer {
            //Assert.assertEquals(Success<List<Movie>>(emptyList()), it)
            assertThat(it).isEqualTo(Success<List<Movie>>(emptyList()))
        }
        viewModel.mainViewState.observeForever(observer)
        //when
        repo.getMoviesCallback(callback)
    }

    @Test
    fun `Get Movies returns Not Empty Result`() {
        doAnswer {
            val callBack: RepoCallBack<List<Movie>> = it.arguments[0] as RepoCallBack<List<Movie>>
            callBack.onSuccess(listOf(Movie("title", "picture", "overview")))
        }.whenever(repo).getMoviesCallback(any(RepoCallBack::class))
        val observer: Observer<MainViewState> = Observer {
            //Assert.assertEquals(Success(listOf(Movie("title", "picture", "overview"))), it)
            assertThat(it).isEqualTo(Success(listOf(Movie("title", "picture", "overview"))))
        }

        viewModel.mainViewState.observeForever(observer)
        repo.getMoviesCallback(callback)
    }


    @Test
    fun `Get Movies returns Error`() {
        doAnswer {
            val callBack: RepoCallBack<List<Movie>> = it.arguments[0] as RepoCallBack<List<Movie>>
            callBack.onError("error")
        }.whenever(repo).getMoviesCallback(any(RepoCallBack::class))
        val observer: Observer<MainViewState> = Observer {
            //Assert.assertEquals(Error("error"), it)
            assertThat(it).isEqualTo(Error("error"))
        }

        viewModel.mainViewState.observeForever(observer)
        repo.getMoviesCallback(callback)
    }

    @Test
    fun `Get Movies returns success with null`() {
        doAnswer {
            val callBack: RepoCallBack<List<Movie>> = it.arguments[0] as RepoCallBack<List<Movie>>
            callBack.onSuccess(null)
        }.whenever(repo).getMoviesCallback(any(RepoCallBack::class))
        val observer: Observer<MainViewState> = Observer {
            //Assert.assertEquals(Success(null),it)
            assertThat(it).isEqualTo(Success(null))
        }

        viewModel.mainViewState.observeForever(observer)
        repo.getMoviesCallback(callback)
    }

    @Test
    fun `Get Movies returns error with null`() {
        doAnswer {
            val callBack: RepoCallBack<List<Movie>> = it.arguments[0] as RepoCallBack<List<Movie>>
            callBack.onError(null)
        }.whenever(repo).getMoviesCallback(any(RepoCallBack::class))
        val observer: Observer<MainViewState> = Observer {
            assertThat(it).isEqualTo(Error(null))
        }
        viewModel.mainViewState.observeForever(observer)
        repo.getMoviesCallback(callback)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Get Movies Coroutine returns data`() {
        val movies: List<Movie> = listOf(Movie("title","image","overview"))
        mainCoroutineRule.runBlockingTest {
            whenever(repo.getMoviesCoroutine()).thenReturn(Result.Success(movies))
            val observer: Observer<MainViewState> = Observer {
                assertThat(it).isEqualTo(Success(movies))
            }
            viewModel.mainViewState.observeForever(observer)
            repo.getMoviesCoroutine()
        }

    }


    @ExperimentalCoroutinesApi
    @Test
    fun `Get Movies Coroutine returns error`() {
        val e = Exception("error")
        mainCoroutineRule.runBlockingTest {
            whenever(repo.getMoviesCoroutine()).thenReturn(Result.Error(e))
            val observer: Observer<MainViewState> = Observer {
                assertThat(it).isEqualTo(Error(e.message))
            }
            viewModel.mainViewState.observeForever(observer)
            repo.getMoviesCoroutine()
        }

    }
}