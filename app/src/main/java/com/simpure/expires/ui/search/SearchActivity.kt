package com.simpure.expires.ui.search

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.simpure.expires.R
import com.simpure.expires.databinding.ActivitySearchBinding
import com.simpure.expires.ui.BaseActivity

class SearchActivity : BaseActivity() {

    override fun initData() {


    }

    override fun initView() {

    }

    override fun onClick(v: View?) {

    }

    private lateinit var mBinding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        setActivityTheme(mBinding.llTop)
    }

}