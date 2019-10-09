package com.simpure.expires.ui.commodity

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.DataBindingUtil.inflate
import android.view.LayoutInflater
import com.simpure.expires.R
import com.simpure.expires.databinding.ItemInventoryBinding
import com.simpure.expires.model.Inventory
import com.simpure.expires.ui.CommodityClickCallback

class InventoryAdapter(/*private val mCommodityClickCallback: CommodityClickCallback*/) :
    RecyclerView.Adapter<InventoryAdapter.ViewHolder>() {


    private lateinit var mInventoryList: List<Inventory>

    fun setInventoryList(inventoryList: List<Inventory>) {
        mInventoryList = inventoryList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemInventoryBinding = inflate(
            LayoutInflater.from(parent.context), R.layout.item_inventory,
            parent, false
        )
//        binding.callback = mCommodityClickCallback
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (::mInventoryList.isInitialized) mInventoryList.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!::mInventoryList.isInitialized) return

        holder.binding.inventory = mInventoryList[position]
        holder.binding.executePendingBindings()
    }

    class ViewHolder(val binding: ItemInventoryBinding) :
        RecyclerView.ViewHolder(binding.root)
}
