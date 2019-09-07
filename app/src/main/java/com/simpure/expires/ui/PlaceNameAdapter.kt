package com.simpure.expires.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.toColor
import androidx.recyclerview.widget.RecyclerView
import com.simpure.expires.R
import com.simpure.expires.data.Place
import com.simpure.expires.utilities.getCompatColor

class PlaceNameAdapter(private val context: Context, private val placeList: List<Place>) :
    RecyclerView.Adapter<PlaceNameAdapter.ViewHolder>() {

    private var selectedPlacePosition = 0

    fun setSelectedPlace(position: Int) {
        this.selectedPlacePosition = position
    }

    fun getSelectedPlacePosition() = selectedPlacePosition

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_place_name, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return placeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder) {

            tvName.text = placeList[position].name
            if (position == selectedPlacePosition) {
                tvName.setTextColor(context.getCompatColor(R.color.text_expires_days))
                tvName.textSize = 36f
            } else {
                tvName.setTextColor(context.getCompatColor(R.color.bg_comm_action))
                tvName.textSize = 20f
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvName: TextView = itemView.findViewById(R.id.tvName)

    }
}