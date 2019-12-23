package com.simpure.expires.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.*
import com.google.zxing.activity.CaptureActivity
import com.google.zxing.util.Constant
import com.simpure.expires.R
import com.simpure.expires.databinding.ActivitySplashBinding
import com.simpure.expires.ui.home.CommodityHomeActivity
import com.simpure.expires.utilities.Constants.APP_MODEL
import com.simpure.expires.utilities.Constants.MODEL_EXPIRES
import com.simpure.expires.utilities.Constants.MODEL_SCAN

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
                goScanActivity()
            }
        }
    }

    private fun goScanActivity() {
        PermissionUtils.permission(PermissionConstants.CAMERA)
            .rationale { shouldRequest -> ToastUtils.showShort(shouldRequest.toString()) }
            .callback(object : PermissionUtils.FullCallback {
                override fun onGranted(permissionsGranted: List<String>) {
                    LogUtils.d(permissionsGranted)
//                    SystemClock.sleep(1000)
                    val intent = Intent(this@SplashActivity, CaptureActivity::class.java)
                    startActivityForResult(intent, Constant.REQ_QR_CODE)
//                    overridePendingTransition(R.anim.translate_fade_in, R.anim.translate_fade_out);
                    finish()
                }

                override fun onDenied(
                    permissionsDeniedForever: List<String>,
                    permissionsDenied: List<String>
                ) {
                    LogUtils.d(permissionsDeniedForever, permissionsDenied)
                    if (permissionsDeniedForever.isNotEmpty()) {
                        ToastUtils.showShort("showOpenAppSettingDialog")
                        return
                    }
                    ToastUtils.showShort("requestCamera")
                }
            })
            .theme { activity -> ScreenUtils.setFullScreen(activity) }
            .request()
    }
}