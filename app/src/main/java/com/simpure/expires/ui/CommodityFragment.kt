package com.simpure.expires.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.simpure.expires.databinding.FragmentPlaceBinding
import com.simpure.expires.utilities.InjectorUtils
import com.simpure.expires.viewmodel.CommodityListViewModel

class CommodityFragment : Fragment() {

    val TAG = "CommodityFragment"
    private val KEY_COMMODITY_ID = "product_id"


    private val viewModel: CommodityListViewModel by viewModels {
        InjectorUtils.provideCommodityHomeViewModelFactory(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentPlaceBinding.inflate(inflater, container, false)
//        binding.rvCommodityList.layoutManager = LinearLayoutManager(context)
//        binding.rvCommodityList.adapter = PlaceAdapter(context!!, placeList)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    /** Creates commodity fragment for specific commodity ID  */
    fun forCommodity(commodityId: Long): CommodityFragment {
        val fragment = CommodityFragment()
        val args = Bundle()
        args.putLong(KEY_COMMODITY_ID, commodityId)
        fragment.arguments = args
        return fragment
    }
}