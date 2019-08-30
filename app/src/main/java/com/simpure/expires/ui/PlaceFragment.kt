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
import com.simpure.expires.data.entry.CommodityEntity
import com.simpure.expires.databinding.FragmentPlaceBinding
import com.simpure.expires.model.Commodity
import com.simpure.expires.viewmodel.CommodityListViewModel

class PlaceFragment : Fragment() {

    val TAG = "ProductListFragment"

    private var mCurrentPlaceId = 0

    private var mPlaceAdapter: PlaceAdapter? = null

    private var mBinding: FragmentPlaceBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_place, container, false)

        mPlaceAdapter = PlaceAdapter(context!!, mCommodityClickCallback)
        mBinding!!.rvCommodityList.adapter = mPlaceAdapter

        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProvider(this).get(CommodityListViewModel::class.java)

        subscribeUi(viewModel.commodities)
    }

    private fun subscribeUi(liveData: LiveData<List<CommodityEntity>>) {
        // 当数据更改时更新列表
        liveData.observe(this,
            Observer<List<Commodity>> { myCommodities ->
                if (myCommodities != null) {
                    mBinding!!.isLoading = false
                    mPlaceAdapter!!.setCommodityList(myCommodities)
                } else {
                    mBinding!!.isLoading = true
                }
                // 异步执行更改
                mBinding!!.executePendingBindings()
            })
    }

    private val mCommodityClickCallback = CommodityClickCallback { commodity ->
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            (activity as CommodityHomeActivity).showCommodityDetail(commodity)
        }
    }

}