package com.simpure.expires.ui

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.inflate
import androidx.recyclerview.widget.RecyclerView
import com.simpure.expires.BR
import com.simpure.expires.R
import com.simpure.expires.databinding.ItemPlaceNameBinding


class PlaceNameAdapter(private val context: Context) :
    RecyclerView.Adapter<PlaceNameAdapter.ViewHolder>() {

    private var selectedPlacePosition = 0
    private var mSelectedPosition = -1
    private lateinit var mPlaceList: List<String>

    fun setSelectedPlace(position: Int) {
        this.selectedPlacePosition = position
    }

    fun getSelectedPlacePosition() = selectedPlacePosition

    fun setSelectedPosition(position: Int) {
        if (mSelectedPosition == position) return
        Log.e(javaClass.simpleName, position.toString())
        mSelectedPosition = position

        notifyDataSetChanged()
    }

    fun setPlaceList(placeList: List<String>) {
        mPlaceList = placeList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemPlaceNameBinding = inflate(
            LayoutInflater.from(context), R.layout.item_place_name,
            parent, false
        )
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return mPlaceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = DataBindingUtil.getBinding<ItemPlaceNameBinding>(holder.itemView)
        with(binding!!) {

            Log.e("AAA", mPlaceList[position])
            setVariable(BR.placeName, mPlaceList[position])
            setVariable(
                BR.isSelected,
                position == mSelectedPosition
            )

            executePendingBindings()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    object PlaceNameBinds {
        @BindingAdapter("isSelected")
        @JvmStatic
        fun setBold(view: TextView, isSelected: Boolean) {
            if (isSelected) {
                view.setBackgroundResource(R.drawable.bg_rounded_rectangle_gray_6)
                view.setTypeface(null, Typeface.BOLD)
            } else {
                view.background = null
                view.setTypeface(null, Typeface.NORMAL)
            }
        }
    }

}