package com.simpure.expires.ui.login

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.simpure.expires.BasicApp
import com.simpure.expires.R
import com.simpure.expires.databinding.ActivityLoginBinding
import com.simpure.expires.ui.BaseActivity
import com.simpure.expires.utilities.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginActivity : BaseActivity() {

    private lateinit var mBinding: ActivityLoginBinding

    override fun onClick(v: View?) {
        mBinding.apply {
            when (v!!) {
                tvLoginThird -> thirdSign = true
                tvLogin -> toast("login")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setActivityTheme(mBinding.clLoginContainer)

        mBinding.apply {
            thirdSign = false
            tvLoginThird.setOnClickListener(this@LoginActivity)
            etEmail.addTextChangedListener {
                ivEmailClear.visibility = if (it.isNullOrEmpty()) View.GONE else View.VISIBLE
            }
            etPassword.addTextChangedListener {
                ivPasswordClear.visibility = if (it.isNullOrEmpty()) View.GONE else View.VISIBLE
            }
            etConfirmPassword.addTextChangedListener {
                ivConfirmPasswordClear.visibility =
                    if (it.isNullOrEmpty()) View.GONE else View.VISIBLE
            }
            etConfirmPassword.setOnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    toast("enter")
//                    LoginUtil(this@LoginActivity).login(etEmail.text, etPassword.text)
                }

                return@setOnKeyListener false
            }
            etConfirmPassword.setOnEditorActionListener { v, actionId, event ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> toast("done")
                }
                return@setOnEditorActionListener true
            }
            tvLogin.setOnClickListener(this@LoginActivity)
        }
        LoginUtil(this).checkUserStatus()
//        startAct(CommodityHomeActivity::class.java)
    }


}