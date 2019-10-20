package com.simpure.expires.view.popup

import android.annotation.SuppressLint
import androidx.recyclerview.widget.LinearLayoutManager
import com.lxj.xpopup.core.PositionPopupView
import com.lxj.xpopup.util.XPopupUtils
import com.simpure.expires.R
import com.simpure.expires.data.Place
import com.simpure.expires.ui.CommodityHomeActivity
import com.simpure.expires.ui.PlaceNameAdapter
import kotlinx.android.synthetic.main.popup_place.view.*

@SuppressLint("ViewConstructor")
class PlacePopup(activity: CommodityHomeActivity) : PositionPopupView(activity) {

    private var mActivity: CommodityHomeActivity = activity
    private lateinit var mPlaceList: List<Place>
    private lateinit var mAdapter: PlaceNameAdapter

    fun setPlaceList(placeList: List<Place>) {
        mPlaceList = placeList
        if (!::mAdapter.isInitialized) {
            val layoutManager = LinearLayoutManager(mActivity)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            rvPlace.layoutManager = layoutManager
            mAdapter = PlaceNameAdapter(mActivity)
            rvPlace.adapter = mAdapter
        }
        mAdapter.setPlaceList(placeList)
    }

    override fun getImplLayoutId(): Int {
        return R.layout.popup_place
    }

    override fun getPopupWidth(): Int {
        return XPopupUtils.getWindowWidth(context)
    }
}