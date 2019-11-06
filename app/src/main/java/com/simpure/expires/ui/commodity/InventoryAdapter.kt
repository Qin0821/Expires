package com.simpure.expires.ui.commodity

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.DataBindingUtil.inflate
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.BindingAdapter
import com.simpure.expires.R
import com.simpure.expires.databinding.ItemInventoryBinding
import com.simpure.expires.model.Inventory
import android.widget.ImageView
import com.simpure.expires.view.popup.InventoryClickListener


class InventoryAdapter(
    private var inventoryList: List<Inventory>,
    private val isEdit: Boolean = false,
    private val listener: InventoryClickListener? = null/*private val mCommodityClickCallback: CommodityClickCallback*/
) :
    RecyclerView.Adapter<InventoryAdapter.ViewHolder>() {


//    private lateinit var mInventoryList: List<Inventory>

    fun setInventoryList(inventoryList: List<Inventory>) {
        this.inventoryList = inventoryList
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
        return inventoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.isSelected = false
        holder.binding.inventory = inventoryList[position]
        holder.binding.isMulti = inventoryList.size > 1 && isEdit
        holder.binding.executePendingBindings()
        if (isEdit)
            holder.binding.rlInventoryItem.setOnClickListener {
                listener?.onClick(inventoryList[position])
                holder.binding.ivInventorySelector.requestFocus()
                holder.binding.isSelected = true
            }
    }

    class ViewHolder(val binding: ItemInventoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    object InventoryBinds {
        @JvmStatic
        @BindingAdapter("imageResource")
        fun setImageSrc(view: ImageView, src: Int) {
            view.setImageResource(src)
        }
    }
}
