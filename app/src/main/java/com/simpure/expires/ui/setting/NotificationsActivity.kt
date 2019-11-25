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
import com.simpure.expires.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_notifications.*

class NotificationsActivity : BaseActivity(), View.OnTouchListener {

    private var lastY = 0f
    private var topOffset = 0
    private var bottomOffset = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        when (v!!) {
            mBinding.llFrom -> {
                setNotificationsDragAnim(mBinding.llFrom, event)
            }
            mBinding.llTo -> {
                setNotificationsDragAnim(mBinding.llTo, event)
            }
        }
        return true
    }

    private fun setNotificationsDragAnim(view: View, event: MotionEvent?) {
        if (null == event) return


        with(mBinding) {
            val isTop = view == llFrom

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastY = event.y
                    if (isTop) onTopTouch = true else onBottomTouch = true
                }
                MotionEvent.ACTION_MOVE -> {

                    val yDistance = event.y - lastY

                    doNotificationsDragAnim(if (isTop) yDistance else -yDistance, isTop)
                    doBallAnim(isTop)
                }
                MotionEvent.ACTION_UP -> {
                    if (isTop) onTopTouch = false else onBottomTouch = false
                }
            }
        }
    }

    private fun doBallAnim(isTop: Boolean) {

        if (topOffset == 0 || bottomOffset == 0) {
            val rect = Rect()
            clNotifications.getGlobalVisibleRect(rect)
            topOffset = rect.top
            bottomOffset = rect.bottom
        }

        val set = ConstraintSet()
        set.clone(mBinding.clNotifications)

        val dragViewId: Int
        val ballView: View
        if (isTop) {
            dragViewId = mBinding.llFrom.id
            ballView = mBinding.rlBeginBall

            val rect = Rect()
            mBinding.llFrom.getGlobalVisibleRect(rect)
            if (rect.top - topOffset > ConvertUtils.dp2px(130f)) {
                set.clear(ballView.id, ConstraintSet.TOP)
                set.connect(
                    ballView.id,
                    ConstraintSet.BOTTOM,
                    dragViewId,
                    ConstraintSet.TOP,
                    ConvertUtils.dp2px(10f)
                )
            } else {
                set.clear(ballView.id, ConstraintSet.BOTTOM)
                set.connect(
                    ballView.id,
                    ConstraintSet.TOP,
                    dragViewId,
                    ConstraintSet.BOTTOM,
                    ConvertUtils.dp2px(10f)
                )
            }
        } else {
            dragViewId = mBinding.llTo.id
            ballView = mBinding.rlEndBall

            val rect = Rect()
            mBinding.llTo.getGlobalVisibleRect(rect)
            if (bottomOffset - rect.bottom > ConvertUtils.dp2px(130f)) {
                set.clear(ballView.id, ConstraintSet.BOTTOM)
                set.connect(
                    ballView.id,
                    ConstraintSet.TOP,
                    dragViewId,
                    ConstraintSet.BOTTOM,
                    ConvertUtils.dp2px(10f)
                )
            } else {
                set.clear(ballView.id, ConstraintSet.TOP)
                set.connect(
                    ballView.id,
                    ConstraintSet.BOTTOM,
                    dragViewId,
                    ConstraintSet.TOP,
                    ConvertUtils.dp2px(10f)
                )
            }
        }

        set.applyTo(mBinding.clNotifications)
    }

    private fun doNotificationsDragAnim(distance: Float, isTop: Boolean) {

        val view = if (isTop) {
            mBinding.shadowTop
        } else {
            mBinding.shadowBottom
        }
        val minHeight = mBinding.llFrom.height / 2
        var newHeight = (view.height + distance).toInt()
        if (newHeight < minHeight) newHeight = minHeight


        val set = ConstraintSet()
        set.clone(mBinding.clNotifications)
        if (isTop) {
            set.connect(view.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
        } else {
            set.connect(
                view.id,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM
            )
        }
        set.connect(view.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
        set.connect(view.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)

        set.constrainHeight(view.id, newHeight)
        set.applyTo(mBinding.clNotifications)

    }

    override fun onClick(v: View?) {
        with(mBinding) {

            when (v!!) {
                tvDone -> finish()
            }

        }
    }

    private lateinit var mBinding: ActivityNotificationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_notifications)
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
            tvDone.setOnClickListener(this@NotificationsActivity)
            llFrom.setOnTouchListener(this@NotificationsActivity)
            llTo.setOnTouchListener(this@NotificationsActivity)
        }
    }

    private fun initData() {

        mBinding.beginTime = "12:00 AM"
        mBinding.endTime = "12:00 PM"
    }
}