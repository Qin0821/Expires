package com.simpure.expires.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.simpure.expires.data.CommodityListRepository

class CommodityListViewModel(
    commodityList: CommodityListRepository,
    private val test: String
): ViewModel() {

    /*val test: LiveData<String>

    *//**
     * Cancel all coroutines when the ViewModel is cleared.
     *//*
    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    init {

    }

    fun addPlantToGarden() {
        viewModelScope.launch {

        }
    }*/

}