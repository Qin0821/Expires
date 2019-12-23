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

abstract class BaseFragmentActivity : BaseActivity(), View.OnClickListener {

//    override val expiresApiService: ExpiresApiService = BasicApp.instance.expiresApiService
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }
//
//    protected fun setActivityTheme(topView: View? = null) {
//        immersionBar {
//            statusBarColor(R.color.appBg)
//            statusBarDarkFont(true)
//            if (null != topView) {
//                titleBarMarginTop(topView)
//            }
//            init()
//        }
//    }

}