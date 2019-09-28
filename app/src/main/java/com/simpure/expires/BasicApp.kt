package com.simpure.expires

import android.app.Application

import com.simpure.expires.data.AppDatabase

/**
 * Android Application class. Used for accessing singletons.
 */
class BasicApp : Application() {

    private var mAppExecutors: AppExecutors? = null

    val database: AppDatabase
        get() = AppDatabase.getInstance(this, mAppExecutors!!)

    val repository: DataRepository
        get() = DataRepository.getInstance(database)

    override fun onCreate() {
        super.onCreate()

        mAppExecutors = AppExecutors()
    }
}
