package com.example.moviedb.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedb.MyApplication
import com.example.moviedb.R
import com.example.moviedb.databinding.ActivityMainBinding
import com.example.moviedb.ui.model.Movie
import java.lang.StringBuilder
import javax.inject.Inject


class MainActivity : AppCompatActivity() {


    private lateinit var adapter: MovieAdapter
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.bind(findViewById(R.id.content))

        viewModel.mainViewState.observe(this, Observer {
            when(it) {
                is Success<*> -> {
                    val movies: List<Movie> = it.data as List<Movie>
                    adapter = MovieAdapter(movies)
                    binding.rvMovies.adapter = adapter
                    binding.rvMovies.layoutManager = LinearLayoutManager(this)
                    binding.rvMovies.setHasFixedSize(true)
                    Toast.makeText(this,movies[0].title,Toast.LENGTH_SHORT).show()
                }
                is Progress -> {
                    Toast.makeText(this,"progress",Toast.LENGTH_SHORT).show()
                }
                is Error -> {
                    Toast.makeText(this,it.error,Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.getMovies()
    }



}

sealed class MainViewState
data class Success<T>(val data: T?): MainViewState()
object Progress: MainViewState()
data class Error (val error: String?): MainViewState()