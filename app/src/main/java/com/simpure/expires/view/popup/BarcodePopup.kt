package com.simpure.expires.view.popup

import android.content.Context
import android.view.LayoutInflater
import com.lxj.xpopup.util.XPopupUtils
import com.simpure.expires.R
import androidx.databinding.DataBindingUtil.inflate
import com.lxj.xpopup.widget.PartShadowContainer
import com.simpure.expires.BR
import com.simpure.expires.data.entity.CommodityEntity

import com.simpure.expires.databinding.PopupBarcodeBinding

class BarcodePopup(context: Context, dismissCallback: () -> Unit) :
    ExpiresPopupView(context, dismissCallback) {
    override fun setMarginTop(top: Int) {

    }

    private val mBinding: PopupBarcodeBinding
    override lateinit var mCommodityDetail: CommodityEntity

    override fun setCommodityDetail(commodityDetail: CommodityEntity) {
        mCommodityDetail = commodityDetail
        mBinding.setVariable(BR.commodityDetail, commodityDetail)
    }

    private val attachPopupContainer: PartShadowContainer =
        findViewById(com.lxj.xpopup.R.id.attachPopupContainer)

    init {
        mBinding =
            inflate(
                LayoutInflater.from(getContext()),
                implLayoutId,
                attachPopupContainer,
                false
            )
        attachPopupContainer.addView(mBinding.root)
    }


    override fun getImplLayoutId(): Int {
        return R.layout.popup_barcode
    }

    override fun getPopupWidth(): Int {
        return XPopupUtils.getWindowWidth(context)
    }
}