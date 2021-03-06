package com.simpure.expires.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.simpure.expires.R
import com.simpure.expires.databinding.FragmentPlaceBinding
import com.simpure.expires.model.CommoditySummaryModel
import com.simpure.expires.ui.CommodityClickCallback
import com.simpure.expires.ui.PlaceAdapter
import com.simpure.expires.view.recycleView.CommodityHomeRecycleViewActionDownListener
import com.simpure.expires.viewmodel.CommoditySummaryViewModel
import java.lang.Exception

class PlaceFragment : Fragment() {

    val TAG = "ProductListFragment"

    private var mCurrentPlaceId = 0

    private var mPlaceAdapter: PlaceAdapter? = null

    private lateinit var mBinding: FragmentPlaceBinding

    private lateinit var mCommoditySummaryViewModel: CommoditySummaryViewModel

    var lastCommodityListY = 0f


    private val onTouchListener = View.OnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> lastCommodityListY = event.y
            MotionEvent.ACTION_MOVE -> {

                val yDistance = event.y - lastCommodityListY
                Log.e(javaClass.simpleName, "yDistance : $yDistance")

                return@OnTouchListener (activity as CommodityHomeActivity).homeTopAnim(yDistance)

            }
            MotionEvent.ACTION_UP -> {
            }
            else -> {
            }
        }
        return@OnTouchListener false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_place, container, false)

        mPlaceAdapter =
            PlaceAdapter(mCommodityClickCallback)
//        mBinding.rvCommodityList.setOnTouchListener(onTouchListener)
        mBinding.rvCommodityList.adapter = mPlaceAdapter
        setMaxFlingVelocity(mBinding.rvCommodityList)


        return mBinding.root
    }

    private fun setMaxFlingVelocity(recycleView: RecyclerView) {
        try {
            val field = RecyclerView::class.java.getDeclaredField("mMaxFlingVelocity")
            field.isAccessible = true
            field.set(recycleView, 4000)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val mCommodityClickCallback = object :
        CommodityClickCallback {
        override fun onClick(commodity: CommoditySummaryModel) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                (activity as CommodityHomeActivity).showCommodityDetail(commodity.id)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUi()
    }

    private fun subscribeUi() {
        mCommoditySummaryViewModel =
            ViewModelProvider(this).get(CommoditySummaryViewModel::class.java)
        mCommoditySummaryViewModel.commodities.observe(this, Observer {

            executePendingBindings(it)
        })
    }

    private fun executePendingBindings(placeSummaryList: List<CommoditySummaryModel>?) {
        // 当数据更改时更新列表
        if (placeSummaryList.isNullOrEmpty()) {
            mBinding.isLoading = true
        } else {
            mBinding.isLoading = false
            mPlaceAdapter!!.setCommodityList(placeSummaryList)
        }
        // 异步执行更改
        mBinding.executePendingBindings()
    }

    fun setCommoditiesSummary(placeSummaryList: List<CommoditySummaryModel>) {
        executePendingBindings(placeSummaryList)
    }

    fun getCommodityListRecycleView() = mBinding.rvCommodityList

    @SuppressLint("ClickableViewAccessibility")
    fun setCommodityListTouchListener(
        onTouchListener: View.OnTouchListener,
        onActionDown: CommodityHomeRecycleViewActionDownListener? = null
    ) {
//        mBinding.rvCommodityList.dispatchTouchEvent()
//        mBinding.rvCommodityList.setActionDownListener(onActionDown)
        mBinding.rvCommodityList.setOnTouchListener(onTouchListener)
    }
}