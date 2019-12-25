package com.simpure.expires.ui

import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil.inflate
import androidx.recyclerview.widget.RecyclerView
import com.simpure.expires.R
import com.simpure.expires.databinding.ItemPlaceNameBinding
import com.simpure.expires.model.commodity.placeName.PlaceNameViewModel


class PlaceNameAdapter(private val activity: BaseActivity, val onPlaceClick: (String) -> Unit) :
    RecyclerView.Adapter<PlaceNameAdapter.ViewHolder>() {

    private var mSelectedPosition = -1
    private lateinit var mPlaceList: List<String>

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
        return ViewHolder(
            inflate(
                LayoutInflater.from(activity), R.layout.item_place_name,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder) {
            Log.e("AAA", mPlaceList[position])
            bind(
                createOnClickListener(mPlaceList[position]),
                PlaceNameViewModel(mPlaceList[position], position == 0)
            )
        }
    }

    override fun getItemCount(): Int {
        return mPlaceList.size
    }

    private fun createOnClickListener(placeName: String): View.OnClickListener {
        return View.OnClickListener {
            onPlaceClick(placeName)
        }
    }

    class ViewHolder(private val binding: ItemPlaceNameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, viewModel: PlaceNameViewModel) {
            with(binding) {
                clickListener = listener
                this.viewModel = viewModel
                executePendingBindings()
            }
        }
    }

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