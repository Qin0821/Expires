package com.simpure.expires.ui.login

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.simpure.expires.BasicApp
import com.simpure.expires.R
import com.simpure.expires.databinding.ActivityLoginBinding
import com.simpure.expires.ui.BaseActivity
import com.simpure.expires.utilities.SPConstants
import com.simpure.expires.utilities.getString
import com.simpure.expires.utilities.put
import com.simpure.expires.utilities.report
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginActivity : BaseActivity() {

    private lateinit var mBinding: ActivityLoginBinding

    override fun onClick(v: View?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)


        LoginUtil(this).checkUserStatus()
//        startAct(CommodityHomeActivity::class.java)
    }


}