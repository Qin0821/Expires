package com.simpure.expires.view.popup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import com.lxj.xpopup.util.XPopupUtils
import com.lxj.xpopup.widget.PartShadowContainer
import com.simpure.expires.BR
import com.simpure.expires.R
import com.simpure.expires.data.entity.CommodityEntity
import com.simpure.expires.databinding.PopupConsumingBinding

class ConsumingPopup(context: Context, val isEdit: Boolean = false, dismissCallback: () -> Unit) :
    ExpiresPopupView(context, dismissCallback) {

    override fun setMarginTop(top: Int) {
//        val lp = mBinding.cardView.layoutParams as ConstraintLayout.LayoutParams
        val set = ConstraintSet()
        set.clone(context, R.layout.popup_consuming)
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
        mBinding.setVariable(BR.commodityDetail, commodityDetail)
    }

    fun setPopupCallback(callback: ConsumingCallback) {
        mBinding.setVariable(BR.callback, callback)
    }

    private val attachPopupContainer: PartShadowContainer =
        findViewById(com.lxj.xpopup.R.id.attachPopupContainer)

    private val mBinding: PopupConsumingBinding

    init {
        mBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(getContext()),
                implLayoutId,
                attachPopupContainer,
                false
            )
        mBinding.apply {
            isEdit = this@ConsumingPopup.isEdit
            minusListener = OnClickListener {
                val detail = commodityDetail
                if (null != detail) {
                    if (detail.amount > 1) detail.amount-- else return@OnClickListener
                    commodityDetail = detail
                }
            }
            plusListener = OnClickListener {
                val detail = commodityDetail
                if (null != detail) {
                    detail.amount++
                    commodityDetail = detail
                }
            }
            expireListener = OnClickListener {
                if (null == isEdit || !isEdit!!) return@OnClickListener

                itemDialogCommodityConsuming.expirePickerContainer.visibility = View.VISIBLE
            }
            productionListener = OnClickListener {
                if (null == isEdit || !isEdit!!) return@OnClickListener


                itemDialogCommodityConsuming.productionPickerContainer.visibility = View.VISIBLE
            }

            attachPopupContainer.addView(this.root)
        }
    }

    override fun getImplLayoutId(): Int {
        return R.layout.popup_consuming
    }

    override fun getPopupWidth(): Int {
        return XPopupUtils.getWindowWidth(context)
    }
}