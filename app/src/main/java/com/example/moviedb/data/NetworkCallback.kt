package com.example.moviedb.data

interface NetworkCallback<T> {
    fun onSuccess(data: T?)
    fun onError(error: String?)
}