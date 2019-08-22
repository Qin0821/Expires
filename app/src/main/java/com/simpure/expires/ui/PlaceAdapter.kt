package com.simpure.expires.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.simpure.expires.R
import com.simpure.expires.data.Commodity
import com.simpure.expires.data.Place
import com.simpure.expires.utilities.getCompatColor

class PlaceAdapter(private val context: Context, private val commodityList: List<Commodity>) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    private var selectedPlacePosition = 0

    fun setSelectedPlace(position: Int) {
        this.selectedPlacePosition = position
    }

    fun getSelectedPlacePosition() = selectedPlacePosition

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_commodity_overview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return commodityList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder) {

            label
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val label: View = itemView.findViewById(R.id.label)
        val tvExpireDay: TextView = itemView.findViewById(R.id.tvExpireDay)
        val tvCommodityName: TextView = itemView.findViewById(R.id.tvCommodityName)
        val tvCommodityDesc: TextView = itemView.findViewById(R.id.tvCommodityDesc)

    }
}