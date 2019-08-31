package com.simpure.expires.utilities

import android.content.Context
import android.os.Build
import org.joda.time.DateTime
import org.joda.time.Days

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

fun Long.calcExpirationDate(): Int {

    val calcTime = DateTime(this)
    val nowDate = DateTime()

    return Days.daysBetween(nowDate, calcTime).getDays();
}