package com.example.moviedb.helper

import android.os.Handler
import android.os.Looper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.Exception
import java.util.concurrent.Callable

class Threading {
    companion object {
        fun dispatchMain(block: Action) {
            val mainHandler = Handler(Looper.getMainLooper())
            mainHandler.post {
                try {
                    block.run()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        fun <T> async(
            task: Callable<T>
        ): Disposable {
            return async(task,null,null,Schedulers.io())
        }


        fun <T> async(
            task: Callable<T>,
            finished: Consumer<T>?,
        ): Disposable {
            return async(task, finished, null, Schedulers.io())
        }


        fun <T> async(
            task: Callable<T>,
            finished: Consumer<T>,
            onError: Consumer<Throwable>?
        ): Disposable {
            return async(task, finished, onError, Schedulers.io())
        }


        fun <T> async(
            task: Callable<T>,
            finished: Consumer<T>?,
            onError: Consumer<Throwable>?,
            scheduler: Scheduler
        ): Disposable {
            return Single.fromCallable(task)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(finished, onError)
        }

    }
}