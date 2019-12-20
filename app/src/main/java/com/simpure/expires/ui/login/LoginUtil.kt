package com.simpure.expires.ui.login

import com.simpure.expires.BasicApp
import com.simpure.expires.ui.BaseActivity
import com.simpure.expires.utilities.SPConstants
import com.simpure.expires.utilities.getString
import com.simpure.expires.utilities.put
import com.simpure.expires.utilities.report
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginUtil(private val activity: BaseActivity) {


    fun checkUserStatus() {

        val email = SPConstants.EMAIL.getString()
        if (email.isEmpty()) return
        val password = SPConstants.PASSWORD.getString()
        if (password.isEmpty()) return

        login(email, password)
    }

    fun login(email: String, password: String) {
//        activity.expiresApiService.login(email, password)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                SPConstants.EMAIL.put(email)
//                SPConstants.PASSWORD.put(password)
//                activity.finish()
//            }, {
//                it.report()
//            })
    }

    fun logout() {
        val emial = SPConstants.EMAIL.getString()
        val password = SPConstants.PASSWORD.getString()
    }

    private fun checkToken() {

        val token = SPConstants.TOKEN.getString()
        // todo
    }
}