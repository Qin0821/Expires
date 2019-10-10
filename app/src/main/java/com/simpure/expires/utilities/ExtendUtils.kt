package com.simpure.expires.utilities

import android.content.Context
import android.os.Build
import android.os.Looper
import android.widget.Toast
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.Duration

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

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
//    if (Looper.myLooper() == null) {
//        Looper.prepare()
//    }
    Toast.makeText(this, message, duration).show()
//    Looper.loop()
}