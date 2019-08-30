package com.simpure.expires.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.simpure.expires.BasicApp
import com.simpure.expires.DataRepository
import com.simpure.expires.data.entry.CommodityEntity

/**
 * The ViewModel for PlaceFragment
 */
class CommodityListViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: DataRepository

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private val mObservableProducts: MediatorLiveData<List<CommodityEntity>> = MediatorLiveData()

    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    val commodities: LiveData<List<CommodityEntity>>
        get() = mObservableProducts

    init {

        // set by default null, until we get data from the database.
        mObservableProducts.value = null

        mRepository = (application as BasicApp).repository
        val commodity = mRepository.commodities

        // observe the changes of the commodities from the database and forward them
        mObservableProducts.addSource<List<CommodityEntity>>(
            commodity
        ) { mObservableProducts.setValue(it) }
    }

//    fun searchProducts(query: String): LiveData<List<CommodityEntity>> {
//        return mRepository.searchCommodities(query)
//    }
}
