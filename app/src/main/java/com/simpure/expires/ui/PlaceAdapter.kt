package com.simpure.expires.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.simpure.expires.R
import com.simpure.expires.databinding.ItemCommodityOverviewBinding
import com.simpure.expires.model.CommoditySummaryModel

class PlaceAdapter(private val mCommodityClickCallback: CommodityClickCallback) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    private var selectedPlacePosition = 0

    fun setSelectedPlace(position: Int) {
        this.selectedPlacePosition = position
    }

    fun getSelectedPlacePosition() = selectedPlacePosition

    internal var mCommoditySummaryList: List<CommoditySummaryModel>? = null

    init {
        setHasStableIds(true)
    }

    fun setCommodityList(commoditySummaryList: List<CommoditySummaryModel>) {
        if (mCommoditySummaryList == null) {
            mCommoditySummaryList = commoditySummaryList
            notifyItemRangeInserted(0, commoditySummaryList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return mCommoditySummaryList!!.size
                }

                override fun getNewListSize(): Int {
                    return commoditySummaryList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return mCommoditySummaryList!![oldItemPosition].id == commoditySummaryList[newItemPosition].id
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    val newCommodity = commoditySummaryList[newItemPosition]
                    val oldCommodity = mCommoditySummaryList!![oldItemPosition]
                    return (newCommodity.id == oldCommodity.id
                            && newCommodity.name == oldCommodity.name
                            && newCommodity.productionDate == oldCommodity.productionDate
                            && newCommodity.expiryDate == oldCommodity.expiryDate)
                }
            })
            mCommoditySummaryList = commoditySummaryList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCommodityOverviewBinding = inflate(
            LayoutInflater.from(parent.context), R.layout.item_commodity_overview,
            parent, false
        )
        binding.callback = mCommodityClickCallback
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.commodity = mCommoditySummaryList!![position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return if (mCommoditySummaryList == null) 0 else mCommoditySummaryList!!.size
    }

    override fun getItemId(position: Int): Long {
        return mCommoditySummaryList!![position].id.toLong()
    }

    class ViewHolder(val binding: ItemCommodityOverviewBinding) :
        RecyclerView.ViewHolder(binding.root)

}