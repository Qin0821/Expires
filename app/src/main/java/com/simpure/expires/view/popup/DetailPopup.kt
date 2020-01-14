package com.simpure.expires.view.popup

import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.lxj.xpopup.util.XPopupUtils
import com.lxj.xpopup.widget.PartShadowContainer
import com.simpure.expires.BR
import com.simpure.expires.R
import com.simpure.expires.data.entity.CommodityEntity
import com.simpure.expires.databinding.PopupDetailBinding

class DetailPopup(context: Context, dismissCallback: () -> Unit) :
    ExpiresPopupView(context, dismissCallback) {
    override fun setMarginTop(top: Int) {


    }

    private val mBinding: PopupDetailBinding
    override lateinit var mCommodityDetail: CommodityEntity

    override fun setCommodityDetail(commodityDetail: CommodityEntity) {
        mCommodityDetail = commodityDetail
        mBinding.setVariable(BR.commodityDetail, commodityDetail)
    }

    private val attachPopupContainer: PartShadowContainer =
        findViewById(com.lxj.xpopup.R.id.attachPopupContainer)

    init {
        mBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(getContext()),
                implLayoutId,
                attachPopupContainer,
                false
            )
        attachPopupContainer.addView(mBinding.root)
    }

    override fun getImplLayoutId(): Int {
        return R.layout.popup_detail
    }

    override fun getPopupWidth(): Int {
        return XPopupUtils.getWindowWidth(context)
    }
}