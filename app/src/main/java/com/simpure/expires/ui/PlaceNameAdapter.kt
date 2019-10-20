package com.simpure.expires.ui

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil.inflate
import androidx.recyclerview.widget.RecyclerView
import com.simpure.expires.BR
import com.simpure.expires.R
import com.simpure.expires.data.Place
import com.simpure.expires.databinding.ItemPlaceNameBinding
import com.simpure.expires.utilities.getCompatColor


class PlaceNameAdapter(private val context: Context) :
    RecyclerView.Adapter<PlaceNameAdapter.ViewHolder>() {

    private var selectedPlacePosition = 0
    private lateinit var mSelectedName: String
    private lateinit var mPlaceList: List<Place>

    fun setSelectedPlace(position: Int) {
        this.selectedPlacePosition = position
    }

    fun getSelectedPlacePosition() = selectedPlacePosition

    fun setSelectedName(selectName: String) {
        mSelectedName = selectName
    }

    fun setPlaceList(placeList: List<Place>) {
        mPlaceList = placeList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemPlaceNameBinding = inflate(
            LayoutInflater.from(parent.context), R.layout.item_place_name,
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mPlaceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder.binding) {

            setVariable(
                BR.isSelected,
                mPlaceList[position].name == mSelectedName
            )

            setVariable(BR.placeName, mPlaceList[position].name)

            tvName.text = mPlaceList[position].name
            if (position == selectedPlacePosition) {
                tvName.setTextColor(context.getCompatColor(R.color.text_expires_days))
                tvName.textSize = 36f
            } else {
                tvName.setTextColor(context.getCompatColor(R.color.bg_comm_action))
                tvName.textSize = 20f
            }

            executePendingBindings()
        }
    }

    class ViewHolder(val binding: ItemPlaceNameBinding) : RecyclerView.ViewHolder(binding.root)

    @BindingAdapter("isSelected")
    fun setBold(view: TextView, isSelected: Boolean) {
        if (isSelected) {
            view.setTypeface(null, Typeface.BOLD)
        } else {
            view.setTypeface(null, Typeface.NORMAL)
        }
    }
}