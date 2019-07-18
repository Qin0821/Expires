package com.simpure.expires.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.simpure.expires.databinding.FragmentBoxBinding
import com.simpure.expires.utilities.InjectorUtils
import com.simpure.expires.viewmodels.CommodityListViewModel

class BoxFragment: Fragment() {

    private val viewModel: CommodityListViewModel by viewModels {
        InjectorUtils.provideCommodityHomeViewModelFactory(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentBoxBinding.inflate(inflater, container, false)
        val boxAdapter = BoxAdapter()
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}