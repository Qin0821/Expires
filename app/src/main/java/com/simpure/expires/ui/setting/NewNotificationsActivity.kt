package com.simpure.expires.ui.setting

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.ConvertUtils
import com.simpure.expires.R
import com.simpure.expires.databinding.ActivityNotificationsBinding
import com.simpure.expires.databinding.ActivityNotificationsNewBinding
import com.simpure.expires.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_notifications.*

class NewNotificationsActivity : BaseActivity() {


    override fun onClick(v: View?) {
        with(mBinding) {

            when (v!!) {
                tvDone -> finish()
            }

        }
    }

    private lateinit var mBinding: ActivityNotificationsNewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_notifications_new)
        setActivityTheme(mBinding.llLayout)

        initData()
        initView()
        initListener()
    }

    private fun initView() {
        mBinding.apply {
            //            llFrom.performClick()
//            llTo.performClick()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListener() {

        mBinding.apply {
            tvDone.setOnClickListener(this@NewNotificationsActivity)
        }
    }

    private fun initData() {

    }
}