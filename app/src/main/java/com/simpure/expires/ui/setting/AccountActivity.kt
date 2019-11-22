package com.simpure.expires.ui.setting

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.simpure.expires.R
import com.simpure.expires.databinding.ActivityAccountBinding
import com.simpure.expires.model.AccountModel
import com.simpure.expires.ui.BaseActivity
import com.simpure.expires.utilities.toast

class AccountActivity : BaseActivity() {
    override fun onClick(v: View?) {

        with(mBinding) {

            when (v!!) {
                tvDone -> finish()
                clNickname -> {
//                    toast("nickname")
                }
                clEmail -> {
                    toast("email")
                }
                clApple -> {
                    toast("apple")

                }
                clFacebook -> {
                    toast("facebook")

                }
                clTwitter -> {
                    toast("twitter")

                }
                clGoogle -> {
                    toast("google")

                }
                clMicrosoft -> {
                    toast("microsoft")

                }
            }
        }
    }

    private lateinit var mBinding: ActivityAccountBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_account)
        setActivityTheme(mBinding.llLayout)

        initData()
        initListener()
    }

    private fun initListener() {

        mBinding.apply {

            tvDone.setOnClickListener(this@AccountActivity)
            clNickname.setOnClickListener(this@AccountActivity)
            clEmail.setOnClickListener(this@AccountActivity)
            clApple.setOnClickListener(this@AccountActivity)
            clFacebook.setOnClickListener(this@AccountActivity)
            clTwitter.setOnClickListener(this@AccountActivity)
            clGoogle.setOnClickListener(this@AccountActivity)
            clMicrosoft.setOnClickListener(this@AccountActivity)
        }
    }

    private fun initData() {

        mBinding.apply {
            account = AccountModel("Qin", "liuqin0821@gmail.com")
        }

    }

}