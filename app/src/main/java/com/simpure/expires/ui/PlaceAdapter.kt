package com.simpure.expires.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.simpure.expires.R
import com.simpure.expires.data.entry.CommodityEntity
import com.simpure.expires.databinding.ItemCommodityOverviewBinding
import com.simpure.expires.model.Commodity

class PlaceAdapter(private val mCommodityClickCallback: CommodityClickCallback) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    private var selectedPlacePosition = 0

    fun setSelectedPlace(position: Int) {
        this.selectedPlacePosition = position
    }

    fun getSelectedPlacePosition() = selectedPlacePosition

    internal var mCommodityList: List<Commodity>? = null

    init {
        setHasStableIds(true)
    }

    fun setCommodityList(commodityList: List<Commodity>) {
        if (mCommodityList == null) {
            mCommodityList = commodityList
            notifyItemRangeInserted(0, commodityList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return mCommodityList!!.size
                }

                override fun getNewListSize(): Int {
                    return commodityList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return mCommodityList!![oldItemPosition].id === commodityList[newItemPosition].id
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    val newCommodity = commodityList[newItemPosition]
                    val oldCommodity = mCommodityList!![oldItemPosition]
                    return (newCommodity.id === oldCommodity.id
                            && newCommodity.name == oldCommodity.name
                            && newCommodity.expirationDate === oldCommodity.expirationDate)
                }
            })
            mCommodityList = commodityList
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
        holder.binding.commodity = mCommodityList!![position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return if (mCommodityList == null) 0 else mCommodityList!!.size
    }

    override fun getItemId(position: Int): Long {
        return mCommodityList!![position].id.toLong()
    }

    class ViewHolder(val binding: ItemCommodityOverviewBinding) :
        RecyclerView.ViewHolder(binding.root)

}