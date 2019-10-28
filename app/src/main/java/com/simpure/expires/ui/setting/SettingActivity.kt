package com.simpure.expires.ui.setting

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.simpure.expires.R
import com.simpure.expires.databinding.ActivitySettingBinding
import com.simpure.expires.ui.BaseActivity

class SettingActivity : BaseActivity() {

    override fun initData() {


    }

    override fun initView() {

    }

    override fun onClick(v: View?) {

    }

    private lateinit var mBinding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_setting)
        setActivityTheme(mBinding.tvTitle)
    }

}