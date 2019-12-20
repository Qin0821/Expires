package com.simpure.expires

import android.app.Application
import com.simpure.expires.api.ExpiresApiService

import com.simpure.expires.data.AppDatabase
import kotlin.properties.Delegates

/**
 * Android Application class. Used for accessing singletons.
 */
class BasicApp : Application() {

    private var mAppExecutors: AppExecutors? = null

    val database: AppDatabase
        get() = AppDatabase.getInstance(this, mAppExecutors!!)

    val repository: DataRepository
        get() = DataRepository.getInstance(database)

    val expiresApiService by lazy {
        ExpiresApiService.create()
    }

    companion object {
        var instance: BasicApp by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        mAppExecutors = AppExecutors()
    }
}
