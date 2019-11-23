package com.simpure.expires.ui.setting.categories

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simpure.expires.R
import com.simpure.expires.databinding.ActivityCategoriesBinding
import com.simpure.expires.ui.BaseActivity
import com.simpure.expires.utilities.toast


class CategoriesActivity : BaseActivity() {
    override fun onClick(v: View?) {
        with(mBinding) {

            when (v!!) {
                ivCanDrag -> {
                    mCanDrag = !mCanDrag
                    touchCallBack.setCanDrag(mCanDrag)
                    mBinding.canDrag = mCanDrag
                }
                ivAdd -> {
                    toast("add")
                }
                tvDone -> finish()
            }
        }
    }

    private lateinit var adapter: CategoriesAdapter
    private var mCanDrag = false
    private lateinit var mBinding: ActivityCategoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_categories)
        setActivityTheme(mBinding.llLayout)

        initData()
        initView()
        initListener()
    }

    val touchCallBack = ItemTouchCallBack()
    private fun initView() {

        val categories = mutableListOf("Fridge", "Bedroom", "Make-ups", "Vegetables", "Medicinal")
        adapter = CategoriesAdapter(this, categories)

        touchCallBack.setOnItemTouchListener(adapter)
        val itemTouchHelper = ItemTouchHelper(touchCallBack)

        mBinding.rvCategories.layoutManager = LinearLayoutManager(this@CategoriesActivity)
        mBinding.rvCategories.adapter = adapter

        itemTouchHelper.attachToRecyclerView(mBinding.rvCategories)


        adapter.setOnItemClickListener(object : CategoriesAdapter.OnItemClickListener {
            override fun onItemClick(
                recyclerView: RecyclerView,
                view: View,
                position: Int,
                obj: String
            ) {
                toast(obj)
            }
        })
    }

    private fun initListener() {

        mBinding.apply {
            ivCanDrag.setOnClickListener(this@CategoriesActivity)
            ivAdd.setOnClickListener(this@CategoriesActivity)
            tvDone.setOnClickListener(this@CategoriesActivity)
        }

    }

    private fun initData() {


    }

}