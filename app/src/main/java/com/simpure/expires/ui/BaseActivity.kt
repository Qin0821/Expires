package com.simpure.expires.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.zxing.activity.CaptureActivity
import com.google.zxing.util.Constant
import com.gyf.immersionbar.ktx.immersionBar
import com.simpure.expires.BasicApp
import com.simpure.expires.R
import com.simpure.expires.api.ExpiresApiService
import com.simpure.expires.utilities.log
import com.simpure.expires.utilities.toast

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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
    }
//    abstract fun initData()
//    abstract fun initView()

}