package com.simpure.expires.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.simpure.expires.BasicApp
import com.simpure.expires.DataRepository
import com.simpure.expires.data.entity.UserEntity

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: DataRepository

    // MediatorLiveData可以观察其他LiveData对象并对它们的排放做出反应。
    private val mObservableUsers: MediatorLiveData<List<UserEntity>> = MediatorLiveData()

    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    val commodities: LiveData<List<UserEntity>>
        get() = mObservableUsers

    init {

        // 默认为null，设值后会继续调用的
        mObservableUsers.value = null

        mRepository = (application as BasicApp).repository
        val allUser = mRepository.allUser

        //  从数据库订阅，类似selector
        mObservableUsers.addSource(allUser) {
            mObservableUsers.value = it
        }
    }
}