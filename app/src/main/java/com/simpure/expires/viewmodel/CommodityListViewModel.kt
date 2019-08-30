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

    // MediatorLiveData可以观察其他LiveData对象并对它们的排放做出反应。
    private val mObservableProducts: MediatorLiveData<List<CommodityEntity>> = MediatorLiveData()

    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    val commodities: LiveData<List<CommodityEntity>>
        get() = mObservableProducts

    init {

        // 默认为null，设值后会继续调用的
        mObservableProducts.value = null

        mRepository = (application as BasicApp).repository
        val commodity = mRepository.commodities

        //  从数据库订阅，类似selector
        mObservableProducts.addSource<List<CommodityEntity>>(
            commodity
        ) { mObservableProducts.setValue(it) }
    }

//    fun searchProducts(query: String): LiveData<List<CommodityEntity>> {
//        return mRepository.searchCommodities(query)
//    }
}
