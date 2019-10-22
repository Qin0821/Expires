package com.simpure.expires.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.simpure.expires.R
import com.simpure.expires.databinding.FragmentPlaceBinding
import com.simpure.expires.model.CommoditySummaryModel
import com.simpure.expires.viewmodel.CommoditySummaryViewModel

class PlaceFragment : Fragment() {

    val TAG = "ProductListFragment"

    private var mCurrentPlaceId = 0

    private var mPlaceAdapter: PlaceAdapter? = null

    private var mBinding: FragmentPlaceBinding? = null

    private lateinit var mCommoditySummaryViewModel: CommoditySummaryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_place, container, false)

        mPlaceAdapter = PlaceAdapter(mCommodityClickCallback)
        mBinding!!.rvCommodityList.adapter = mPlaceAdapter

        return mBinding!!.root
    }

    private val mCommodityClickCallback = CommodityClickCallback { commodity ->
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            (activity as CommodityHomeActivity).showCommodityDetail(commodity.id)
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
            mBinding!!.isLoading = true
        } else {
            mBinding!!.isLoading = false
            mPlaceAdapter!!.setCommodityList(placeSummaryList)
        }
        // 异步执行更改
        mBinding!!.executePendingBindings()
    }

    fun setCommoditiesSummary(placeSummaryList: List<CommoditySummaryModel>) {
        executePendingBindings(placeSummaryList)
    }
}