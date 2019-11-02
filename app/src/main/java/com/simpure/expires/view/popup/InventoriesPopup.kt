package com.simpure.expires.view.popup

import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.lxj.xpopup.core.PositionPopupView
import com.lxj.xpopup.util.XPopupUtils
import com.lxj.xpopup.widget.PartShadowContainer
import com.simpure.expires.R
import com.simpure.expires.databinding.PopupInventoriesBinding

class InventoriesPopup(context: Context, type: String) : ExpiresPopupView(context) {

    private val attachPopupContainer: PartShadowContainer =
        findViewById(com.lxj.xpopup.R.id.attachPopupContainer)

    init {
        val binding: PopupInventoriesBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(getContext()),
                implLayoutId,
                attachPopupContainer,
                false
            )
        attachPopupContainer.addView(binding.root)
    }

    override fun getImplLayoutId(): Int {
        return R.layout.popup_inventories
    }

    override fun getPopupWidth(): Int {
        return XPopupUtils.getWindowWidth(context)
    }

}
