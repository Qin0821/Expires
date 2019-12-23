package com.simpure.expires.view.popup

import com.lxj.xpopup.core.PositionPopupView
import com.lxj.xpopup.util.XPopupUtils
import com.simpure.expires.R
import com.simpure.expires.ui.home.CommodityHomeActivity
import kotlinx.android.synthetic.main.popup_add.view.*
import kotlinx.android.synthetic.main.popup_consuming_select.view.*

class AddPopup(val activity: CommodityHomeActivity) : PositionPopupView(activity) {

    override fun doAfterShow() {
        super.doAfterShow()
        clTypeInManually.setOnClickListener {
            dismiss()
            activity.typeInManually()
        }
        clScanBarcode.setOnClickListener {
            dismiss()
            activity.scanBarcode()
        }
    }

    override fun getImplLayoutId(): Int {
        return R.layout.popup_add
    }

    override fun getPopupWidth(): Int {
        return XPopupUtils.getWindowWidth(context)
    }
}