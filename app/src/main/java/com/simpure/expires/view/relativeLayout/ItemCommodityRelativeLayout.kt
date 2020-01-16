package com.simpure.expires.view.relativeLayout

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.RelativeLayout

class ItemCommodityRelativeLayout : RelativeLayout {

    private var isEdit = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    public fun setIsEdit(isEdit: Boolean) {
        this.isEdit = isEdit
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (isEdit) return false
        return super.onInterceptTouchEvent(ev)
    }

}