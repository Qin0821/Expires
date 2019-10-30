package com.simpure.expires.view.popup

import android.content.Context
import com.lxj.xpopup.core.PositionPopupView
import com.lxj.xpopup.util.XPopupUtils
import com.simpure.expires.R

class DetailPopup(context: Context) : PositionPopupView(context) {

    override fun getImplLayoutId(): Int {
        return R.layout.popup_clear
    }

    override fun getPopupWidth(): Int {
        return XPopupUtils.getWindowWidth(context)
    }
}