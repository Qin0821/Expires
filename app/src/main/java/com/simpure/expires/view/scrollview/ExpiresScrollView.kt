package com.simpure.expires.view.scrollview

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView

class ExpiresScrollView : NestedScrollView {

    private lateinit var scrollViewListener: ScrollViewListener

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    public fun setScrollViewListener(scrollViewListener: ScrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    override fun onScrollChanged(x: Int, y: Int, oldX: Int, oldY: Int) {
        super.onScrollChanged(x, y, oldX, oldY)
        if (::scrollViewListener.isInitialized) {
            scrollViewListener.onScrollChanged(this, x, y, oldX, oldY)
        }

    }
}