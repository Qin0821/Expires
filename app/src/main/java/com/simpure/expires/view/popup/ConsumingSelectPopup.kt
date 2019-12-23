package com.simpure.expires.view.popup

import com.lxj.xpopup.core.PositionPopupView
import com.lxj.xpopup.util.XPopupUtils
import com.simpure.expires.R
import com.simpure.expires.ui.home.CommodityHomeActivity
import kotlinx.android.synthetic.main.popup_consuming_select.view.*

class ConsumingSelectPopup(val activity: CommodityHomeActivity) : PositionPopupView(activity) {

    override fun doAfterShow() {
        super.doAfterShow()
        clNewConsuming.setOnClickListener {
            dismiss()
            activity.newConsuming()
        }
        clFromInventories.setOnClickListener {
            dismiss()
            activity.fromInventroies()
        }
    }

    override fun getImplLayoutId(): Int {
        return R.layout.popup_consuming_select
    }

    override fun getPopupWidth(): Int {
        return XPopupUtils.getWindowWidth(context)
    }
}