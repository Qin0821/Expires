package com.simpure.expires.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ScreenSlidePagerAdapter(
    activity: CommodityHomeActivity,
    val placeFragment: PlaceFragment,
    val accountFragment: AccountFragment
) : FragmentStateAdapter(activity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) placeFragment else accountFragment
    }

}
