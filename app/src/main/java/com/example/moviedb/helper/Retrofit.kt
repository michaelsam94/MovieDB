package com.example.moviedb.helper

import com.example.moviedb.data.NetworkCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Retrofit {

    companion object {
        fun <T> doCall(c: Call<T>,networkCallback: NetworkCallback<T>){
            c.enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    networkCallback.onSuccess(response.body())
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    networkCallback.onError(t.message)
                }
            })
        }
    }

}