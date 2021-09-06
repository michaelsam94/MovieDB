package com.example.moviedb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.concurrent.thread

const val BASE_URL = "https://api.themoviedb.org/3/"
const val API_KEY = "6bdd92e829e5beb0f2902f834db79e10"

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.addInterceptor {
            val req = it.request()
            val orignalHttpUrl = req.url()

            val url = orignalHttpUrl.newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

            val reqBuilder = req.newBuilder().url(url)
            val newReq = reqBuilder.build()
            it.proceed(newReq)
        }

        val retrofit =
            Retrofit.Builder()
            .baseUrl(BASE_URL)
                .client(okHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create()).build()

        val service = retrofit.create(MovieService::class.java)
        thread {
            val call : Call<NowPlayingRes> = service.getNowPlaying()
            Log.d("call",call.execute().body().toString())
        }



    }

}