package com.simpure.expires.utilities

import com.blankj.utilcode.util.SPUtils
import com.simpure.expires.BasicApp

fun String.getString(defaultValue: String = ""): String {
    return SPUtils.getInstance().getString(this, defaultValue)
}

fun String.put(value: Any, isCommit: Boolean = false) {
    SPUtils.getInstance().apply {

        when (value) {
            is String -> SPUtils.getInstance().put(this@put, value, isCommit)
            is Int -> SPUtils.getInstance().put(this@put, value, isCommit)
            is Long -> SPUtils.getInstance().put(this@put, value, isCommit)
            is Float -> SPUtils.getInstance().put(this@put, value, isCommit)
            is Boolean -> SPUtils.getInstance().put(this@put, value, isCommit)
            is Set<*> -> {
                try {
                    @Suppress("UNCHECKED_CAST")
                    SPUtils.getInstance().put(this@put, value as Set<String>, isCommit)
                } catch (e: Throwable) {
                    e.report(BasicApp.instance.applicationContext, e.message)
                }
            }
            else -> {
                Throwable("un expect sp value").apply {
                    report(
                        BasicApp.instance.applicationContext,
                        message
                    )
                }
            }
        }
    }
}