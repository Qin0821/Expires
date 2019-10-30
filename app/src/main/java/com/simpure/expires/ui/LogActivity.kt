package com.simpure.expires.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.simpure.expires.R
import com.simpure.expires.utilities.log

abstract class LogActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        "onCreate".log(javaClass.simpleName, Log.VERBOSE)
        super.onCreate(savedInstanceState)
    }

    /**
     * @desc 当activity布局改动时，即setContentView() 或者 addContentView() 方法执行完毕时就会调用该方法
     * Activity 中 View 的 findViewById() 方法都可以放到该方法中。
     */
    override fun onContentChanged() {
        "onContentChanged".log(javaClass.simpleName, Log.VERBOSE)
        super.onContentChanged()
    }

    /**
     * @desc onCreate彻底执行完毕，做最后的初始化工作
     */
    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        "onPostCreate".log(javaClass.simpleName, Log.VERBOSE)
        super.onPostCreate(savedInstanceState, persistentState)
    }

    /**
     * @desc 由完全不可见变为可见
     */
    override fun onRestart() {
        "onRestart".log(javaClass.simpleName, Log.VERBOSE)
        super.onRestart()
    }

    /**
     * @desc 由不可见变为可见，此时处于后台可见，还不能与用户交互
     */
    override fun onStart() {
        "onStart".log(javaClass.simpleName, Log.VERBOSE)
        super.onStart()
    }

    /**
     * @desc 常见触发场景：横竖屏切换、切换语言等
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        "onRestoreInstanceState".log(javaClass.simpleName, Log.VERBOSE)
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onNewIntent(intent: Intent?) {
        "onNewIntent".log(javaClass.simpleName, Log.VERBOSE)
        super.onNewIntent(intent)
    }

    override fun onResume() {
        "onResume".log(javaClass.simpleName, Log.VERBOSE)
        super.onResume()
    }

    override fun onPostResume() {
        "onPostResume".log(javaClass.simpleName, Log.VERBOSE)
        super.onPostResume()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        "onWindowFocusChanged".log(javaClass.simpleName, Log.VERBOSE)
        super.onWindowFocusChanged(hasFocus)
    }

    /**
     * @desc Activity 无论分发按键事件、触摸事件或者轨迹球事件都会调用
     * 所有调用 Activity#onUserLeaveHint() 的回调都会首先回调 Activity#onUserInteraction() 。
     * Activity 在分发各种事件的时候会调用该方法，
     *
     * 注意：启动另一个 activity ,Activity#onUserInteraction()会被调用两次，
     * 一次是 activity 捕获到事件，
     * 另一次是调用 Activity#onUserLeaveHint() 之前会调用 Activity#onUserInteraction() 。
     */
    override fun onUserInteraction() {
        "onUserInteraction".log(javaClass.simpleName, Log.VERBOSE)
        super.onUserInteraction()
    }

    /**
     * @desc 当用户的操作使一个 activity 准备进入后台时，此方法会像 activity 的生命周期的一部分被调用。
     * 例如，当用户按下 Home 键，Activity#onUserLeaveHint() 将会被调用。
     * 但是当来电导致 activity 自动占据前台（系统自动切换），Activity#onUserLeaveHint() 将不会被回调。
     *
     * 一般监听返回键，是重写 onKeyDown() 方法，
     * 但是 Home 键和 Menu 键就不好监听，
     * 但是可以在 onUserLeaveHint() 方法中监听。
     */
    override fun onUserLeaveHint() {
        "onUserLeaveHint".log(javaClass.simpleName, Log.VERBOSE)
        super.onUserLeaveHint()
    }

    /**
     * @desc 不在处于前台，不可与用户交互
     * 适合保存持久化数据，不允许耗时操作，大量数据保存得另开线程
     */
    override fun onPause() {
        "onPause".log(javaClass.simpleName, Log.VERBOSE)
        super.onPause()
    }

    /**
     * @desc 适合保存瞬态数据，如ui状态、成员变量值等
     * 在onPause之前，与onStop无固定时序关系
     * 常见的触发场景有：横竖屏切换、按下电源键、按下菜单键、切换到别的 Activity 等
     */
    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        "onSaveInstanceState".log(javaClass.simpleName, Log.VERBOSE)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onStop() {
        "onStop".log(javaClass.simpleName, Log.VERBOSE)
        super.onStop()
    }

    override fun onDestroy() {
        "onDestroy".log(javaClass.simpleName, Log.VERBOSE)
        super.onDestroy()
    }
}