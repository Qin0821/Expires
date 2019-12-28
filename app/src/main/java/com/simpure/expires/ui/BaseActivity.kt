package com.simpure.expires.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.simpure.expires.BasicApp
import com.simpure.expires.R
import com.simpure.expires.api.ExpiresApiService
import com.simpure.expires.utilities.log

abstract class BaseActivity : LogActivity(), View.OnClickListener {

    val expiresApiService: ExpiresApiService = BasicApp.instance.expiresApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        initData()
//        initView()
    }

    protected fun setActivityTheme(topView: View? = null) {
        immersionBar {
            statusBarColor(R.color.appBg)
            statusBarDarkFont(true)
//            fitsSystemWindows(true)
            if (null != topView) {
                titleBarMarginTop(topView)
            }
            init()
        }
    }

    protected fun setExpiresTheme(colorResId: Int = R.color.appBg) {
        immersionBar {
            transparentStatusBar()
            statusBarDarkFont(true)
            navigationBarColor(colorResId)
            navigationBarDarkIcon(true)
            init()
        }
    }


//    abstract fun initData()
//    abstract fun initView()

}