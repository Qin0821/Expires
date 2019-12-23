package com.simpure.expires.view.popup

import android.annotation.SuppressLint
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.lxj.xpopup.core.PositionPopupView
import com.lxj.xpopup.util.XPopupUtils
import com.simpure.expires.R
import com.simpure.expires.ui.home.CommodityHomeActivity
import com.simpure.expires.ui.PlaceNameAdapter
import kotlinx.android.synthetic.main.popup_place.view.*
import java.lang.Exception

@SuppressLint("ViewConstructor")
class PlacePopup(activity: CommodityHomeActivity) : PositionPopupView(activity) {

    private var mActivity: CommodityHomeActivity = activity
    private lateinit var mPlaceList: List<String>
    private lateinit var mAdapter: PlaceNameAdapter

    private lateinit var xLimit: Array<Int>
    private lateinit var yLimit: Array<Int>

    private lateinit var mSelectedPlace: String

    fun setPlaceList(placeList: List<String>) {
        mPlaceList = placeList
        if (!::mAdapter.isInitialized) {
            val layoutManager = LinearLayoutManager(mActivity)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            rvPlace.layoutManager = layoutManager
            mAdapter = PlaceNameAdapter(mActivity)
            rvPlace.adapter = mAdapter
        }
        mAdapter.setPlaceList(placeList)

//        rvPlace.measure(
//            0,
//            MeasureSpec.makeMeasureSpec(0 /* any */, MeasureSpec.UNSPECIFIED)
//        )
    }

    fun setMoveLimit(xLimit: Array<Int>, yLimit: Array<Int>) {
        this.xLimit = xLimit
        this.yLimit = yLimit
    }

    fun setMoveXY(moveX: Float, moveY: Float) {

//        Log.e(javaClass.simpleName, (moveX).toString())
//        Log.e(javaClass.simpleName, (moveY).toString())
//        Log.e(javaClass.simpleName, "-----------")
        if (!::xLimit.isInitialized || !::yLimit.isInitialized) return

        if (moveX < xLimit[0] || moveX > xLimit[1] || moveY < yLimit[0] || moveY > yLimit[1]) {

            this.dismiss()
        } else {
            try {
                val y = moveY - yLimit[0]
                val position: Int = (y / ConvertUtils.dp2px(42f)).toInt()
                mAdapter.setSelectedPosition(position)
                mSelectedPlace = mPlaceList[position]

            } catch (e: Exception) {
                Log.e("AAA", y.toString())
                Log.e("AAA", ConvertUtils.dp2px(42f).toString())
            }
        }

    }

    fun getSelectPlace(): String {
        return if (::mSelectedPlace.isInitialized) mSelectedPlace else ""
    }

    override fun getImplLayoutId(): Int {
        return R.layout.popup_place
    }

    override fun getPopupWidth(): Int {
        return XPopupUtils.getWindowWidth(context)
    }
}