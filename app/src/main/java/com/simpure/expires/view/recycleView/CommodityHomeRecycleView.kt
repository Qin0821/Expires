package com.simpure.expires.view.recycleView

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import java.util.jar.Attributes

class CommodityHomeRecycleView : RecyclerView {

    private lateinit var mActionDownListener: CommodityHomeRecycleViewActionDownListener

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
//        Log.e("CommodityHomeActivity", "onInterceptTouchEvent -------------- ${e!!.action}")
        if (e!!.action == MotionEvent.ACTION_DOWN) {
//            mActionDownListener.onActionDown(e)
            return true
        }
        return super.onInterceptTouchEvent(e)
    }

    fun setActionDownListener(listener: CommodityHomeRecycleViewActionDownListener) {
        mActionDownListener = listener
    }
}

