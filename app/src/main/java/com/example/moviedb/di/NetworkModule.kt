package com.example.moviedb.di

import com.example.moviedb.data.network.API_KEY
import com.example.moviedb.data.network.BASE_URL
import com.example.moviedb.data.network.MovieService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory




@Module
class NetworkModule {

    @Provides
    fun provideRetrofitMovieService(okHttpClient: OkHttpClient): MovieService {
        val retrofit =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build()

        return retrofit.create(MovieService::class.java)
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
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
        return okHttpClientBuilder.build()
    }


}