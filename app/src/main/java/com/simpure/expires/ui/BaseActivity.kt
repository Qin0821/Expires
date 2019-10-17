package com.simpure.expires.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.simpure.expires.R

abstract class BaseActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        initData()
        initView()
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


    abstract fun initData()
    abstract fun initView()

}