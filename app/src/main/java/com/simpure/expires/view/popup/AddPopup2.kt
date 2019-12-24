package com.simpure.expires.view.popup

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.simpure.expires.R

class AddPopup2(val context: Context, val dismissCallback: () -> Unit) : PopupWindow(context) {

    private val mAlpha: Float = 0.5f

    init {
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        width = ViewGroup.LayoutParams.MATCH_PARENT
        isOutsideTouchable = true
        isFocusable = true
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val contentView: View = LayoutInflater.from(context).inflate(
            R.layout.popup_add,
            null, false
        )
        setContentView(contentView)
//        showBackgroundAnimator()
    }

    /**
     * 窗口显示，窗口背景透明度渐变动画
     */
    private fun showBackgroundAnimator() {
        if (mAlpha >= 1f) return
        val animator: ValueAnimator = ValueAnimator.ofFloat(1.0f, mAlpha)
        animator.addUpdateListener { animation ->
            val alpha = animation.animatedValue as Float
            setWindowBackgroundAlpha(alpha)
        }
        animator.duration = 360
        animator.start()
    }

    private fun setWindowBackgroundAlpha(alpha: Float) {
        if (context is Activity) {
            val window = context.window
            val layoutParams = window.attributes
            layoutParams.alpha = alpha
            window.attributes = layoutParams
        }
    }

    override fun dismiss() {
        dismissCallback()
        super.dismiss()
    }
}