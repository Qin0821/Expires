package com.simpure.expires.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.simpure.expires.BasicApp
import com.simpure.expires.DataRepository
import com.simpure.expires.data.entity.CommodityEntity
import com.simpure.expires.model.CommodityModel

/**
 * The ViewModel for PlaceFragment
 */
class CommoditySummaryViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: DataRepository

    // MediatorLiveData可以观察其他LiveData对象并对它们的排放做出反应。
    private val mObservableCommodities: MediatorLiveData<List<CommodityModel>> = MediatorLiveData()

    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    val commodities: LiveData<List<CommodityModel>>
        get() = mObservableCommodities

    init {

        // 默认为null，设值后会继续调用的
        mObservableCommodities.value = null

        mRepository = (application as BasicApp).repository
        val commodity = mRepository.commoditiesSummary

        //  从数据库订阅，类似selector
        mObservableCommodities.addSource<List<CommodityModel>>(commodity) {
            mObservableCommodities.setValue(it)
        }
    }
}
