package com.example.moviedb.data

interface RepoCallBack<T> {
    fun onSuccess(data: T?)
    fun onError(error: String?)
}