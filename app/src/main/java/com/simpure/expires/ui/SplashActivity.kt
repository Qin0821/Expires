package com.simpure.expires.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.*
import com.simpure.expires.R
import com.simpure.expires.databinding.ActivitySplashBinding
import com.simpure.expires.ui.home.CommodityHomeActivity
import com.simpure.expires.utilities.Constants.APP_MODEL
import com.simpure.expires.utilities.Constants.MODEL_EXPIRES
import com.simpure.expires.utilities.Constants.MODEL_SCAN
import com.simpure.expires.utilities.goScanAct

class SplashActivity : BaseActivity() {
    override fun onClick(v: View?) {


    }

    private lateinit var appModel: String
    private lateinit var mBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        val spInstance = SPUtils.getInstance()
        spInstance.put(APP_MODEL, MODEL_SCAN)

        appModel = spInstance.getString(APP_MODEL)


        when (appModel) {
            MODEL_EXPIRES -> {
                startActivity(Intent(this, CommodityHomeActivity::class.java))
                finish()
            }
            MODEL_SCAN -> {
                goScanAct()
            }
        }
    }
}