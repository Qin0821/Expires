package com.simpure.expires.ui.commodity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil.getBinding
import androidx.recyclerview.widget.RecyclerView
import com.simpure.expires.R
import androidx.databinding.DataBindingUtil.inflate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.zxing.util.BarcodeGenerator
import com.simpure.expires.BR
import com.simpure.expires.data.entity.CommodityEntity
import com.simpure.expires.databinding.DialogCommodityBinding
import com.simpure.expires.ui.CommodityHomeActivity
import com.simpure.expires.viewmodel.CommodityDetailViewModel
import kotlin.concurrent.thread

class CommodityAdapter(
    private val activity: CommodityHomeActivity,
    private val commodityId: Int
) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: DialogCommodityBinding =
            if (convertView == null) {
                inflate(
                    LayoutInflater.from(parent!!.context),
                    R.layout.dialog_commodity,
                    parent,
                    false
                )
            } else {
                getBinding(convertView)!!
            }

//        val factory = CommodityDetailViewModel.Factory(activity.application, commodityId)
//        val model = ViewModelProvider(activity, factory).get(CommodityDetailViewModel::class.java)
        val viewModel = ViewModelProvider(activity).get(CommodityDetailViewModel::class.java)
//        viewModel.initCommodity(commodityId)
        viewModel.commodityDetail.observe(activity, Observer {
            if (null == it) return@Observer

            binding.setVariable(BR.commodityDetail, it)

            showInventories(it, binding, parent)
            showBarcode(it, binding, parent)
        })


        return binding.root
    }

    private fun showInventories(
        it: CommodityEntity,
        binding: DialogCommodityBinding,
        parent: ViewGroup?
    ) {
        if (!it.inventories.isNullOrEmpty()) {
            with(binding.itemInventories.rvInventories) {
                if (null == layoutManager) {
                    layoutManager = object : LinearLayoutManager(
                        parent!!.context,
                        VERTICAL, false
                    ) {
                        override fun canScrollVertically(): Boolean = false
                    }
                    this.layoutManager = layoutManager
                }
                if (null == adapter) {
                    this.isNestedScrollingEnabled = false
                    adapter = InventoryAdapter()
                }

                (adapter as InventoryAdapter).setInventoryList(it.inventories)
            }
        }
    }

    private fun showBarcode(
        it: CommodityEntity,
        binding: DialogCommodityBinding,
        parent: ViewGroup?
    ) {
        if (it.barcode.isNotEmpty()) {
            thread {
                with(binding.itemBarcode.ivBarcode) {
                    val barcodeBitmap =
                        BarcodeGenerator.getBarcodeImage(it.barcode, this.width, this.height)
                    activity.runOnUiThread {
                        // 上面的width和height会莫名其妙变成0导致这里报错
                        // 先不处理
                        // 如果app在其他地方必须用到sd卡读取权限
                        // 这里做sd卡的缓存
                        // 如果没有必要的sd卡权限，这里做成存数据库
                        // http://ddrv.cn/a/119750
//                        if (null == barcodeBitmap) {
//                            parent!!.context.toast("条形码解析失败")
//                        }
                        this.setImageBitmap(barcodeBitmap)
                    }
                }
            }

        }
    }

    override fun getItem(position: Int): Any = commodityId

    override fun getItemId(position: Int): Long = commodityId.toLong()

    override fun getCount(): Int = 1


    class ViewHolder(val binding: DialogCommodityBinding) :
        RecyclerView.ViewHolder(binding.root)
}
