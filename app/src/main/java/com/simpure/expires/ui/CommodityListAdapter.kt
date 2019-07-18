package com.simpure.expires.ui

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import java.util.*

class CommodityListAdapter(private val viewList: List<View>) : PagerAdapter() {


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return viewList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(viewList[position])
        return viewList[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(viewList[position])
    }

}