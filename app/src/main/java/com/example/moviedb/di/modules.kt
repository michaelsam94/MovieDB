package com.example.moviedb.di

import com.example.moviedb.data.MovieRepositoryImp
import com.example.moviedb.data.network.API_KEY
import com.example.moviedb.data.network.BASE_URL
import com.example.moviedb.data.network.MovieRemoteDataSourceImp
import com.example.moviedb.data.network.MovieService
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModules = module {

    single(named("OkHttpClient")) {
        val okHttpClientBuilder = with(OkHttpClient.Builder()) {
            this.addInterceptor {
                val req = it.request()
                val originalHttpUrl = req.url()

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()

                val reqBuilder = req.newBuilder().url(url)
                val newReq = reqBuilder.build()
                it.proceed(newReq)
            }
        }
        okHttpClientBuilder.build()
    }

    single(named("retrofit")) {
        val retrofit =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(get(named("OkHttpClient")))
                .addConverterFactory(GsonConverterFactory.create()).build()
        retrofit.create(MovieService::class.java)
    }

    single(named("movieRemoteDataSource")){
        MovieRemoteDataSourceImp(get(named("retrofit")))
    }

    single {
        MovieRepositoryImp(
            get(named("movieRemoteDataSource"))
        )
    }

}