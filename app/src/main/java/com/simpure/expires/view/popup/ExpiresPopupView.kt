package com.simpure.expires.view.popup

import android.content.Context

import android.view.ViewGroup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.widget.PartShadowContainer
import com.lxj.xpopup.R
import com.lxj.xpopup.animator.PopupAnimator
import com.lxj.xpopup.animator.ScrollScaleAnimator
import com.lxj.xpopup.util.XPopupUtils
import com.lxj.xpopup.enums.PopupAnimation.ScaleAlphaFromCenter
import com.simpure.expires.data.entity.CommodityEntity

@Suppress("LeakingThis")
abstract class ExpiresPopupView(context: Context, val dismissCallback: () -> Unit) :
    BasePopupView(context) {

    protected abstract var mCommodityDetail: CommodityEntity

    private val attachPopupContainer: PartShadowContainer = findViewById(R.id.attachPopupContainer)

    override fun getPopupLayoutId(): Int {
        return com.simpure.expires.R.layout._expires_attach_popup_view
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

    abstract fun setCommodityDetail(commodityDetail: CommodityEntity)
    abstract fun setMarginTop(top: Int)

    override fun dismiss() {
        dismissCallback()
        super.dismiss()
    }
}