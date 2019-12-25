package com.simpure.expires.view.popup

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simpure.expires.R
import com.simpure.expires.ui.PlaceNameAdapter
import com.simpure.expires.ui.home.CommodityHomeActivity

class PlacePopup2(
    val activity: CommodityHomeActivity,
    val dismissCallback: () -> Unit,
    private val onPlaceClick: (String) -> Unit
) :
    PopupWindow(activity) {

    private lateinit var mAdapter: PlaceNameAdapter
    private lateinit var mPlaceList: List<String>

    private val rvPlace: RecyclerView

    init {
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        width = ViewGroup.LayoutParams.WRAP_CONTENT
        isOutsideTouchable = true
        isFocusable = true
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val contentView: View = LayoutInflater.from(activity).inflate(
            R.layout.popup_place,
            null, false
        )
        setContentView(contentView)
        rvPlace = contentView.findViewById(R.id.rvPlace)
    }

    fun setPlaceList(placeList: List<String>) {

        mPlaceList = placeList
        if (!::mAdapter.isInitialized) {
            val layoutManager = LinearLayoutManager(activity)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            rvPlace.layoutManager = layoutManager
            mAdapter = PlaceNameAdapter(activity) { place ->
                onPlaceClick(place)
                dismiss()
            }
            rvPlace.adapter = mAdapter
        }
        mAdapter.setPlaceList(placeList)
    }

    override fun dismiss() {
        dismissCallback()
        super.dismiss()
    }
}