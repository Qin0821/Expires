package com.simpure.expires.ui.search

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.simpure.expires.R
import com.simpure.expires.databinding.ActivitySearchBinding
import com.simpure.expires.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity() {

    override fun initData() {


    }

    override fun initView() {

    }

    override fun onClick(v: View?) {

        when (v!!) {
            clSearch -> {
                prepareSearch()
            }
        }
    }

    private fun prepareSearch() {

        prepareSearchAnim()

        // editText 的 onChangeListener，根据输入内容显示搜索结果

        // 回车键监听，结束搜索动画
        // cancel监听，置空搜索内容，结束搜索动画
        // placeSwitch监听，

        endSearchAnim()
    }

    private fun endSearchAnim() {

        // 以下操作同时进行
        // 1. 搜索框下移并伸长；
        // 2. 搜索icon移到中间；
        // 3. placeSwitch 向上收起
        // 4. 软键盘消失
        // 5. 光标消失
    }

    private fun prepareSearchAnim() {

        // 以下操作同时进行
        // 1. 搜索框上移并缩短；
        // 2. 搜索icon左移；
        // 3. placeSwitch 向下展开

        // 上述动画执行完成之后
        // 1. 软键盘弹出
        // 2. 光标闪烁
    }

    private lateinit var mBinding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        setActivityTheme(mBinding.llTop)

        mBinding.clSearch.setOnClickListener(this)
    }

}