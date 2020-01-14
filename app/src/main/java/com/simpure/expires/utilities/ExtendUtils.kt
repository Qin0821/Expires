package com.simpure.expires.utilities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Build
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.zxing.activity.CaptureActivity
import com.google.zxing.util.Constant
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

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
//    if (Looper.myLooper() == null) {
//        Looper.prepare()
//    }
    Toast.makeText(this, message, duration).show()
//    Looper.loop()
}

fun Context.startAct(intent: Intent) {
    this.startActivity(intent)
}

fun Context.startAct(cls: Class<*>) {
    this.startActivity(Intent(this, cls))
}

fun Activity.goScanAct() {
    PermissionUtils.permission(PermissionConstants.CAMERA)
        .rationale { shouldRequest -> ToastUtils.showShort(shouldRequest.toString()) }
        .callback(object : PermissionUtils.FullCallback {
            override fun onGranted(permissionsGranted: List<String>) {
                LogUtils.d(permissionsGranted)
                val intent = Intent(this@goScanAct, CaptureActivity::class.java)
                this@goScanAct.startActivityForResult(intent, Constant.REQ_QR_CODE)
//                    overridePendingTransition(R.anim.translate_fade_in, R.anim.translate_fade_out);
            }

            override fun onDenied(
                permissionsDeniedForever: List<String>,
                permissionsDenied: List<String>
            ) {
                LogUtils.d(permissionsDeniedForever, permissionsDenied)
                if (permissionsDeniedForever.isNotEmpty()) {
                    ToastUtils.showShort("showOpenAppSettingDialog")
                    return
                }
                ToastUtils.showShort("requestCamera")
            }
        })
        .theme { activity -> ScreenUtils.setFullScreen(activity) }
        .request()
}

fun Any.log(tag: String, level: Int = Log.ERROR) {
    when (level) {
        Log.VERBOSE -> Log.v(tag, this.toString())
        Log.INFO -> Log.i(tag, this.toString())
        Log.DEBUG -> Log.d(tag, this.toString())
        Log.WARN -> Log.w(tag, this.toString())
        Log.ERROR -> Log.e(tag, this.toString())
    }
}

fun Long.getIntervalDays(otherDay: Long) {

    return
}

fun Long.calcExpirationDate(): Int {

    val calcTime = DateTime(this)
    val nowDate = DateTime()

    return Days.daysBetween(nowDate, calcTime).days
}

fun View.fadeIn(listener: Animator.AnimatorListener? = null) {
    this.apply {
        alpha = 0f
        visibility = View.VISIBLE

        animate()
            .alpha(1f)
            .setDuration(360)
            .setListener(listener)
    }
}

fun View.fadeOut() {
    this.animate()
        .alpha(0f)
        .setDuration(360)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                this@fadeOut.visibility = View.GONE
            }
        })
}

fun View.isVisibleAll(): Boolean {
    val rect = Rect()
    this.getLocalVisibleRect(rect)
    return rect.top == 0 && rect.bottom == this.height
}

fun Throwable.report(context: Context? = null, toastMsg: String? = null) {
    // todo report error

    toastMsg?.let { context?.toast(it) }
}