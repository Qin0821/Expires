package com.simpure.expires.view.popup

import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.lxj.xpopup.core.PositionPopupView
import com.lxj.xpopup.util.XPopupUtils
import com.lxj.xpopup.widget.PartShadowContainer
import com.simpure.expires.BR
import com.simpure.expires.R
import com.simpure.expires.data.entity.CommodityEntity
import com.simpure.expires.databinding.PopupInventoriesBinding
import com.simpure.expires.ui.commodity.InventoryAdapter

class InventoriesPopup(context: Context, private val isToUseType: Boolean) :
    ExpiresPopupView(context) {
    override fun setMarginTop(top: Int) {


    }

    fun setPopupCallback(callback: InventoriesCallback) {
        mBinding.setVariable(BR.callback, callback)
    }

    private val mBinding: PopupInventoriesBinding
    override lateinit var mCommodityDetail: CommodityEntity

    override fun setCommodityDetail(commodityDetail: CommodityEntity) {
        mCommodityDetail = commodityDetail
        mBinding.setVariable(BR.commodityDetail, commodityDetail)
        mBinding.setVariable(BR.isToUseType, isToUseType)

        with(mBinding.rvInventories) {
            if (null == layoutManager) {
                layoutManager = object : LinearLayoutManager(
                    context,
                    VERTICAL, false
                ) {
                    override fun canScrollVertically(): Boolean = false
                }
                this.layoutManager = layoutManager
            }
            if (null == adapter) {
                adapter = InventoryAdapter(commodityDetail.inventories, true)
            } else {
                (adapter as InventoryAdapter).setInventoryList(commodityDetail.inventories)
            }
        }
    }

    private val attachPopupContainer: PartShadowContainer =
        findViewById(com.lxj.xpopup.R.id.attachPopupContainer)
    private lateinit var mAdapter: InventoryAdapter

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
        return R.layout.popup_inventories
    }

    override fun getPopupWidth(): Int {
        return XPopupUtils.getWindowWidth(context)
    }

}
