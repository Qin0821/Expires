package com.simpure.expires.ui.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.zxing.activity.CaptureActivity
import com.google.zxing.util.Constant
import com.simpure.expires.R
import com.simpure.expires.databinding.ActivitySearchBinding
import com.simpure.expires.ui.BaseActivity
import com.simpure.expires.utilities.toast
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity() {

    override fun onClick(v: View?) {

        when (v!!) {
            clSearch -> {
                prepareSearch()
            }
            itemScanBarcode -> {
                goScanActivity()

            }
        }
    }

    private fun prepareSearch() {

        prepareSearchAnim()

        // editText 的 onChangeListener，根据输入内容显示搜索结果

        // 回车键监听，结束搜索动画
        // cancel监听，置空搜索内容，结束搜索动画
        // placeSwitch监听，

        endSearchAnim()
    }

    private fun endSearchAnim() {

        // 以下操作同时进行
        // 1. 搜索框下移并伸长；
        // 2. 搜索icon移到中间；
        // 3. placeSwitch 向上收起
        // 4. 软键盘消失
        // 5. 光标消失
    }

    private fun prepareSearchAnim() {

        // 以下操作同时进行
        // 1. 搜索框上移并缩短；
        // 2. 搜索icon左移；
        // 3. placeSwitch 向下展开

        // 上述动画执行完成之后
        // 1. 软键盘弹出
        // 2. 光标闪烁
    }

    private lateinit var mBinding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        setActivityTheme(mBinding.layoutSearch)

        initListener()
    }

    private fun initListener() {

        mBinding.apply {
            itemScanBarcode.setOnClickListener(this@SearchActivity)
            clSearch.setOnClickListener(this@SearchActivity)
        }

    }

    private fun goScanActivity() {
        PermissionUtils.permission(PermissionConstants.CAMERA)
            .rationale { shouldRequest -> ToastUtils.showShort(shouldRequest.toString()) }
            .callback(object : PermissionUtils.FullCallback {
                override fun onGranted(permissionsGranted: List<String>) {
                    LogUtils.d(permissionsGranted)
                    val intent = Intent(this@SearchActivity, CaptureActivity::class.java)
                    startActivityForResult(intent, Constant.REQ_QR_CODE)
//                    overridePendingTransition(R.anim.translate_fade_in, R.anim.translate_fade_out);
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        // 扫描结果回调
        if (requestCode == Constant.REQ_QR_CODE && resultCode == RESULT_OK) {
            val bundle = data?.extras
            val scanResult = bundle?.getString(Constant.INTENT_EXTRA_KEY_QR_SCAN)
            Log.e("AAA", scanResult.toString())
            toast(scanResult.toString())
//            mBinding.btInventories.text = scanResult
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}