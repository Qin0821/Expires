package com.simpure.expires.view.popup

import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import com.lxj.xpopup.util.XPopupUtils
import com.lxj.xpopup.widget.PartShadowContainer
import com.simpure.expires.BR
import com.simpure.expires.R
import com.simpure.expires.data.entity.CommodityEntity
import com.simpure.expires.databinding.PopupClearBinding

class ConsumingPopup(context: Context) : ExpiresPopupView(context) {

    override fun setMarginTop(top: Int) {
//        val lp = mBinding.cardView.layoutParams as ConstraintLayout.LayoutParams
        val set = ConstraintSet()
        set.clone(context, R.layout.popup_clear)
        set.connect(
            R.id.cardView,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP,
            top
        )
        set.connect(
            R.id.cardView,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START
        )
        set.connect(
            R.id.cardView,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END
        )
        set.applyTo(mBinding.clPopupClearLayout)
    }

    override lateinit var mCommodityDetail: CommodityEntity

    override fun setCommodityDetail(commodityDetail: CommodityEntity) {
        mCommodityDetail = commodityDetail
        mBinding.setVariable(BR.isEdit, true)
        mBinding.setVariable(BR.commodityDetail, commodityDetail)
    }

    fun setPopupCallback(callback: ConsumingCallback) {
        mBinding.setVariable(BR.callback, callback)
    }

    private val attachPopupContainer: PartShadowContainer =
        findViewById(com.lxj.xpopup.R.id.attachPopupContainer)

    private val mBinding: PopupClearBinding

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
        return R.layout.popup_clear
    }

    override fun getPopupWidth(): Int {
        return XPopupUtils.getWindowWidth(context)
    }
}