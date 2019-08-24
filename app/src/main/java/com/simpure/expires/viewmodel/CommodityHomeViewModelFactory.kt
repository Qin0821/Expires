package com.simpure.expires.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.simpure.expires.data.CommodityRepository

/**
 * Factory for creating a [CommodityHomeViewModel] with a constructor that takes a
 * [CommodityRepository].
 */
class CommodityHomeViewModelFactory(
    private val repository: CommodityRepository
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CommodityHomeViewModel(repository) as T
    }
}