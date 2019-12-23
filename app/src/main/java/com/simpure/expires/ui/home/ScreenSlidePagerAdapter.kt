package com.simpure.expires.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ScreenSlidePagerAdapter(activity: CommodityHomeActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount() = 2

    override fun createFragment(p0: Int): Fragment {
        return if (p0 == 0) PlaceFragment() else AccountFragment()
    }

}
