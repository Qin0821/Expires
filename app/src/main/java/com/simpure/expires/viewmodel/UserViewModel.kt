package com.simpure.expires.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.simpure.expires.BasicApp
import com.simpure.expires.DataRepository
import com.simpure.expires.data.entry.UserEntity

class UserViewModel : AndroidViewModel {

    var user: ObservableField<UserEntity> = ObservableField()
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

    /**
     * A creator is used to inject the user ID into the ViewModel
     *
     *
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the product ID can be passed in a public method.
     */
    class Factory(private val mApplication: Application, private val mUserId: Int) :
        ViewModelProvider.NewInstanceFactory() {

        private val mRepository: DataRepository = (mApplication as BasicApp).repository

        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return UserViewModel(mApplication, mRepository, mUserId) as T
        }
    }

}