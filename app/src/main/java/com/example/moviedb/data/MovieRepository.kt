package com.example.moviedb.data

import com.example.moviedb.ui.model.Movie

interface MovieRepository {

    fun getMovies(repoCallback: RepoCallBack<List<Movie>>) : List<Movie>
}