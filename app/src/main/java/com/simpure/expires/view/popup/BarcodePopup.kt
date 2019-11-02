package com.simpure.expires.view.popup

import android.content.Context
import android.view.LayoutInflater
import com.lxj.xpopup.util.XPopupUtils
import com.simpure.expires.R
import androidx.databinding.DataBindingUtil.inflate
import com.lxj.xpopup.widget.PartShadowContainer

import com.simpure.expires.databinding.PopupBarcodeBinding

class BarcodePopup(context: Context) : ExpiresPopupView(context) {

    private val attachPopupContainer: PartShadowContainer =
        findViewById(com.lxj.xpopup.R.id.attachPopupContainer)

    init {
        val binding: PopupBarcodeBinding =
            inflate(
                LayoutInflater.from(getContext()),
                implLayoutId,
                attachPopupContainer,
                false
            )
        attachPopupContainer.addView(binding.root)
    }


    override fun getImplLayoutId(): Int {
        return R.layout.popup_barcode
    }

    override fun getPopupWidth(): Int {
        return XPopupUtils.getWindowWidth(context)
    }
}