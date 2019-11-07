package com.simpure.expires.view.popup

import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import com.lxj.xpopup.core.PositionPopupView
import com.lxj.xpopup.util.XPopupUtils
import com.lxj.xpopup.widget.PartShadowContainer
import com.simpure.expires.BR
import com.simpure.expires.R
import com.simpure.expires.data.entity.CommodityEntity
import com.simpure.expires.databinding.PopupClearBinding

class ConsumingSelectPopup(context: Context) : PositionPopupView(context) {

    override fun getImplLayoutId(): Int {
        return R.layout.popup_consuming_select
    }

    override fun getPopupWidth(): Int {
        return XPopupUtils.getWindowWidth(context)
    }
}