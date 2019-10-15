package com.simpure.expires.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ktx.immersionBar

abstract class BaseActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityTheme()

        initData()
        initView()
    }

    protected fun setActivityTheme() {
        immersionBar {
            statusBarDarkFont(true)
        }
    }


    abstract fun initData()
    abstract fun initView()

}