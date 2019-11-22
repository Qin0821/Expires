package com.simpure.expires.ui.setting

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.simpure.expires.R
import com.simpure.expires.databinding.ActivityCategoriesBinding
import com.simpure.expires.databinding.ActivityNotificationsBinding
import com.simpure.expires.ui.BaseActivity

class NotificationsActivity : BaseActivity() {
    override fun onClick(v: View?) {


    }

    private lateinit var mBinding: ActivityNotificationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_notifications)
        setActivityTheme(mBinding.llLayout)

        initData()
        initListener()
    }

    private fun initListener() {


    }

    private fun initData() {


    }
}