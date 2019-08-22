package com.simpure.expires.utilities

import android.content.Context
import android.os.Build

fun Context.getCompatColor(id: Int): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.resources.getColor(id, null)
    } else {
        this.resources.getColor(id)
    }
}

fun Long.getIntervalDays(otherDay: Long) {

    return
}