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
import com.simpure.expires.BR
import com.simpure.expires.databinding.DialogCommodityBinding
import com.simpure.expires.ui.CommodityHomeActivity
import com.simpure.expires.viewmodel.CommodityDetailViewModel

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
        viewModel.setCommodityId(commodityId)
        viewModel.commodityDetail.observe(activity, Observer {
            binding.setVariable(BR.commodityDetail, it)
        })

        return binding.root
    }

    override fun getItem(position: Int): Any = commodityId

    override fun getItemId(position: Int): Long = commodityId.toLong()

    override fun getCount(): Int = 1


    class ViewHolder(val binding: DialogCommodityBinding) :
        RecyclerView.ViewHolder(binding.root)
}
