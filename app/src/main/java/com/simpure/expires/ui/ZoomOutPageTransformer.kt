package com.simpure.expires.ui

import android.util.Log
import android.view.View
import androidx.viewpager.widget.ViewPager
import java.lang.Math.max
import kotlin.math.abs

 class ZoomOutPageTransformer : ViewPager.PageTransformer {

    private val MIN_SCALE = 0.85f
    private val MIN_ALPHA = 0.5f

    override fun transformPage(page: View, position: Float) {

        val pageWidth = page.width
        val pageHeight = page.height

        Log.d("ZoomOutPageTransformer", "$page, $position")

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            page.alpha = 0f
        } else if (position <= 1) { // [-1,1]
            // Modify the default slide transition to shrink the page as well
            val scaleFactor = max(MIN_SCALE, 1 - abs(position))
            val vertMargin = pageHeight * (1 - scaleFactor) / 2
            val horzMargin = pageWidth * (1 - scaleFactor) / 2
            if (position < 0) {
                page.translationX = horzMargin - vertMargin / 2
            } else {
                page.translationX = -horzMargin + vertMargin / 2
            }

            // Scale the page down (between MIN_SCALE and 1)
            page.scaleX = scaleFactor
            page.scaleY = scaleFactor

            // Fade the page relative to its size.
            page.alpha = MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA)
        } else {
            // This page is way off-screen to the right.
            page.alpha = 0f
        }
    }

}