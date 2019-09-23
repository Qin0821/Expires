package com.simpure.expires.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.simpure.expires.BasicApp
import com.simpure.expires.DataRepository
import com.simpure.expires.data.entry.UserEntity

class UserViewModel(application: Application) : AndroidViewModel(application) {

    /*var user: ObservableField<UserEntity> = ObservableField()
    private val mObservableUser: LiveData<UserEntity>


    constructor(application: Application, repository: DataRepository, userId: Int) : super(
        application
    ) {
        mObservableUser = repository.loadUser(userId)

    }

    fun getUser(): LiveData<UserEntity> = mObservableUser

    fun setUser(user: UserEntity) {
        this.user.set(user)
    }

    */
    /**
     * A creator is used to inject the user ID into the ViewModel
     *
     *
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the product ID can be passed in a public method.
     *//*
    class Factory(private val mApplication: Application, private val mUserId: Int) :
        ViewModelProvider.NewInstanceFactory() {

        private val mRepository: DataRepository = (mApplication as BasicApp).repository

        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return UserViewModel(mApplication, mRepository, mUserId) as T
        }
    }*/

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
        mObservableUsers.addSource<List<UserEntity>>(allUser) {
            mObservableUsers.setValue(it)
        }
    }

//    fun searchProducts(query: String): LiveData<List<CommodityEntity>> {
//        return mRepository.searchCommodities(query)
//    }

}