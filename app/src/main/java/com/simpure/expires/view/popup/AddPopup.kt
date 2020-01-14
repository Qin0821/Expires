package com.simpure.expires.view.popup

import com.lxj.xpopup.core.PositionPopupView
import com.lxj.xpopup.util.XPopupUtils
import com.simpure.expires.R
import com.simpure.expires.ui.home.CommodityHomeActivity
import kotlinx.android.synthetic.main.popup_add.view.*

class AddPopup(val activity: CommodityHomeActivity) : PositionPopupView(activity) {

    override fun doAfterShow() {
        super.doAfterShow()
        vTypeInManually.setOnClickListener {
            dismiss()
            activity.typeInManually()
        }
        vScanBarcode.setOnClickListener {
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