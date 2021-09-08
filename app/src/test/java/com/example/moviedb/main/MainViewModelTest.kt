package com.example.moviedb.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moviedb.any
import com.example.moviedb.data.MovieRepository
import com.example.moviedb.data.MovieRepositoryImp
import com.example.moviedb.data.RepoCallBack
import com.example.moviedb.ui.main.MainViewModel
import com.example.moviedb.ui.main.Progress
import com.example.moviedb.ui.main.Success
import com.example.moviedb.ui.model.Movie
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.anyObject
import org.mockito.Mockito.mock
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.notNull
import org.mockito.Mockito.`when` as whenever


class MainViewModelTest {

    @get:Rule var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var repo: MovieRepository

    @Before
    fun setup() {
        repo = mock(MovieRepository::class.java)
        viewModel = MainViewModel(repo)
    }

    @Test
    fun `Get Movies Progress`() {
        val callBack: RepoCallBack<List<Movie>> = object : RepoCallBack<List<Movie>> {
            override fun onSuccess(data: List<Movie>?) {

            }

            override fun onError(error: String?) {

            }

        }
        whenever(repo.getMovies(callBack)).thenAnswer {
            (it.arguments[0] as RepoCallBack<*>).onSuccess(any())
        }
        viewModel.getMovies()
        Assert.assertEquals(Progress,viewModel.mainViewState.value)
    }


}