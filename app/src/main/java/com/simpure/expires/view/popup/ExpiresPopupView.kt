package com.simpure.expires.view.popup

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.widget.PartShadowContainer
import com.lxj.xpopup.R
import com.lxj.xpopup.animator.PopupAnimator
import com.lxj.xpopup.animator.ScrollScaleAnimator
import com.lxj.xpopup.util.XPopupUtils
import com.lxj.xpopup.enums.PopupAnimation.ScaleAlphaFromCenter

open class ExpiresPopupView(context: Context) : BasePopupView(context) {

    private val attachPopupContainer: PartShadowContainer = findViewById(R.id.attachPopupContainer)

    init {
        val contentView =
            LayoutInflater.from(getContext()).inflate(implLayoutId, attachPopupContainer, false)
        attachPopupContainer.addView(contentView)
    }

    override fun getPopupLayoutId(): Int {
        return R.layout._xpopup_attach_popup_view
    }

    override fun initPopupContent() {
        super.initPopupContent()
        XPopupUtils.applyPopupSize(popupContentView as ViewGroup, maxWidth, maxHeight) {
            if (popupInfo.isCenterHorizontal) {
                val left =
                    (XPopupUtils.getWindowWidth(context) - attachPopupContainer.measuredWidth) / 2f
                attachPopupContainer.translationX = left
            } else {
                attachPopupContainer.translationX = popupInfo.offsetX.toFloat()
            }
            attachPopupContainer.translationY = popupInfo.offsetY.toFloat()
        }
    }

    override fun getPopupAnimator(): PopupAnimator {
        return ScrollScaleAnimator(popupContentView, ScaleAlphaFromCenter)
    }
}