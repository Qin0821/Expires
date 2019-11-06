package com.simpure.expires.view.popup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.lxj.xpopup.core.PositionPopupView
import com.lxj.xpopup.util.XPopupUtils
import com.lxj.xpopup.widget.PartShadowContainer
import com.simpure.expires.BR
import com.simpure.expires.R
import com.simpure.expires.data.entity.CommodityEntity
import com.simpure.expires.databinding.PopupInventoriesBinding
import com.simpure.expires.model.Inventory
import com.simpure.expires.ui.commodity.InventoryAdapter
import com.simpure.expires.utilities.toast

class InventoriesPopup(context: Context, private val isToUseType: Boolean) :
    ExpiresPopupView(context) {

    private val mContext: Context = context
    private var mBinding: PopupInventoriesBinding

    private var mAmount = 1
    override lateinit var mCommodityDetail: CommodityEntity
    private lateinit var mSelectInventory: Inventory

    init {
        val attachPopupContainer: PartShadowContainer =
            findViewById(com.lxj.xpopup.R.id.attachPopupContainer)
        mBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(getContext()),
                implLayoutId,
                attachPopupContainer,
                false
            )
        attachPopupContainer.addView(mBinding.root)

        setPopupCallback()
    }

    override fun setMarginTop(top: Int) {


    }

    fun setPopupCallback(/*callback: InventoriesCallback*/) {
        val callback = object : InventoriesCallback {
            override fun minusClick() {
                if (!::mSelectInventory.isInitialized || mAmount == 1) return

                mBinding.setVariable(BR.amount, (--mAmount).toString())
            }

            override fun plusClick() {
                if (!::mSelectInventory.isInitialized || mAmount == mSelectInventory.amount) return

                mBinding.setVariable(BR.amount, (++mAmount).toString())

            }

            override fun toUseClick() {
                mContext.toast("toUse")
            }

            override fun throwClick() {
                mContext.toast("throw")
            }
        }
        mBinding.setVariable(BR.callback, callback)
    }

    private val listener = object : InventoryClickListener {
        override fun onClick(inventory: Inventory) {
            mSelectInventory = inventory
            mBinding.tvAmount.setTextColor(context.getColor(R.color.green))
            context.toast(mSelectInventory.amount.toString())
        }
    }

    override fun setCommodityDetail(commodityDetail: CommodityEntity) {
        mCommodityDetail = commodityDetail
        mBinding.setVariable(BR.commodityDetail, commodityDetail)
        mBinding.setVariable(BR.isToUseType, isToUseType)
        mBinding.setVariable(BR.amount, mAmount.toString())

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
                adapter = InventoryAdapter(commodityDetail.inventories, true, listener)
            } else {
                (adapter as InventoryAdapter).setInventoryList(commodityDetail.inventories)
            }
        }
    }

    override fun getImplLayoutId(): Int {
        return R.layout.popup_inventories
    }

    override fun getPopupWidth(): Int {
        return XPopupUtils.getWindowWidth(context)
    }

}
