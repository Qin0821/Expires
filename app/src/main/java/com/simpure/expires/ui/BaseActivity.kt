package com.simpure.expires.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ktx.immersionBar

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityTheme()

        initData()
        initView()
    }

    protected fun setActivityTheme() {
//        ScreenUtils.setTranslucentTheme(this)
        immersionBar {
            statusBarDarkFont(true)
        }
    }


    abstract fun initData()
    abstract fun initView()

}