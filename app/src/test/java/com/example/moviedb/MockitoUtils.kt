package com.example.moviedb

import com.example.moviedb.data.RepoCallBack
import org.mockito.Mockito
import kotlin.reflect.KClass


fun <T> any(kClass: KClass<RepoCallBack<*>>): T = Mockito.any<T>()

