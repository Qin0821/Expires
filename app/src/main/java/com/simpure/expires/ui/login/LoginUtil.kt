package com.simpure.expires.ui.login

import cn.sharesdk.framework.PlatformActionListener
import cn.sharesdk.framework.ShareSDK
import cn.sharesdk.tencent.qq.QQ
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

    fun thirdLogin(platformActionListener: PlatformActionListener) {
        val plat = ShareSDK.getPlatform(QQ.NAME)
        //移除授权状态和本地缓存，下次授权会重新授权
        plat.removeAccount(true)
        //SSO授权，传false默认是客户端授权
        plat.SSOSetting(false)
        //授权回调监听，监听oncomplete，onerror，oncancel三种状态
        plat.platformActionListener = platformActionListener
        //抖音登录适配安卓9.0
        ShareSDK.setActivity(activity)
        //要数据不要功能，主要体现在不会重复出现授权界面
        plat.showUser(null)
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