package com.simpure.expires.ui.setting

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.simpure.expires.R
import com.simpure.expires.databinding.ActivitySettingBinding
import com.simpure.expires.ui.BaseActivity
import com.simpure.expires.ui.setting.categories.CategoriesActivity
import com.simpure.expires.utilities.startAct
import com.simpure.expires.utilities.toast
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity() {

    override fun onClick(v: View?) {
        when (v!!) {
            clAccount -> {
                startAct(AccountActivity::class.java)
            }
            clCatagories -> {
                startAct(CategoriesActivity::class.java)

            }
            clNotifications -> {
                startAct(NotificationsActivity::class.java)

            }
            clShareApp -> {
                toast("share app")
            }
            clRateUs -> {
                toast("rate us")

            }
            clSendFeedback -> {
                toast("send feed")

            }
        }
    }

    private lateinit var mBinding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_setting)
        setActivityTheme(mBinding.tvSetting)

        initData()
        initListener()
    }

    private fun initData() {

        // 通过接口获取
        // todo gvCatagories

        mBinding.apply {
            name = "Qin"
            email = "liuqin0821@gmail.com"
            reminderInterval = "8:00 AM - 9:00 PM"
        }

    }

    private fun initListener() {

        mBinding.clAccount.setOnClickListener(this)
        mBinding.clCatagories.setOnClickListener(this)
        mBinding.clNotifications.setOnClickListener(this)
        mBinding.clShareApp.setOnClickListener(this)
        mBinding.clRateUs.setOnClickListener(this)
        mBinding.clSendFeedback.setOnClickListener(this)

    }

}