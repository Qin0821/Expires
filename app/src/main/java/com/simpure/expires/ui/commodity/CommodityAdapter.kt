package com.simpure.expires.ui.commodity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil.getBinding
import androidx.recyclerview.widget.RecyclerView
import com.simpure.expires.R
import androidx.databinding.DataBindingUtil.inflate
import com.simpure.expires.BR
import com.simpure.expires.databinding.DialogCommodityBinding
import com.simpure.expires.model.Commodity
import com.simpure.expires.ui.PlaceAdapter

class CommodityAdapter(
    private val commodity: Commodity
) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: DialogCommodityBinding = if (convertView == null) {
            inflate(
                LayoutInflater.from(parent!!.context),
                R.layout.dialog_commodity,
                parent,
                false
            )
        } else {
            getBinding(convertView)!!
        }
        binding.setVariable(BR.commodity, commodity)

        return binding.root
    }

    override fun getItem(position: Int): Any = commodity

    override fun getItemId(position: Int): Long = 0L

    override fun getCount(): Int = 1


    class ViewHolder(val binding: DialogCommodityBinding) :
        RecyclerView.ViewHolder(binding.root)
}
