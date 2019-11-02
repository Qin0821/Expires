package com.simpure.expires.view.popup

import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil.inflate

import android.view.ViewGroup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.widget.PartShadowContainer
import com.lxj.xpopup.R
import com.lxj.xpopup.animator.PopupAnimator
import com.lxj.xpopup.animator.ScrollScaleAnimator
import com.lxj.xpopup.util.XPopupUtils
import com.lxj.xpopup.enums.PopupAnimation.ScaleAlphaFromCenter
import com.simpure.expires.databinding.PopupBarcodeBinding
import com.simpure.expires.databinding.PopupClearBinding
import com.simpure.expires.databinding.PopupDetailBinding
import com.simpure.expires.databinding.PopupInventoriesBinding

@Suppress("LeakingThis")
open class ExpiresPopupView(context: Context) : BasePopupView(context) {

    private val attachPopupContainer: PartShadowContainer = findViewById(R.id.attachPopupContainer)
//
//    init {
//        when (this) {
//            is ConsumingPopup -> {
//                val binding: PopupClearBinding =
//                    inflate(
//                        LayoutInflater.from(getContext()),
//                        implLayoutId,
//                        attachPopupContainer,
//                        false
//                    )
//                attachPopupContainer.addView(binding.root)
//            }
//            is InventoriesPopup -> {
//                val binding: PopupInventoriesBinding =
//                    inflate(
//                        LayoutInflater.from(getContext()),
//                        implLayoutId,
//                        attachPopupContainer,
//                        false
//                    )
//                attachPopupContainer.addView(binding.root)
//            }
//            is DetailPopup -> {
//                val binding: PopupDetailBinding =
//                    inflate(
//                        LayoutInflater.from(getContext()),
//                        implLayoutId,
//                        attachPopupContainer,
//                        false
//                    )
//                attachPopupContainer.addView(binding.root)
//            }
//            is BarcodePopup -> {
//                val binding: PopupBarcodeBinding =
//                    inflate(
//                        LayoutInflater.from(getContext()),
//                        implLayoutId,
//                        attachPopupContainer,
//                        false
//                    )
//                attachPopupContainer.addView(binding.root)
//            }
//        }
//    }

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