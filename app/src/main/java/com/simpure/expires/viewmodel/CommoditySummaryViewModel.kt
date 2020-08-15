package com.simpure.expires.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.simpure.expires.BasicApp
import com.simpure.expires.DataRepository
import com.simpure.expires.model.CommoditySummaryModel

/**
 * The ViewModel for PlaceFragment
 */
class CommoditySummaryViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: DataRepository

    // MediatorLiveData可以观察其他LiveData对象并对它们的排放做出反应。
    private val mObservableCommodities: MediatorLiveData<List<CommoditySummaryModel>> =
        MediatorLiveData()

    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    val commodities: LiveData<List<CommoditySummaryModel>>
        get() = mObservableCommodities

    init {

        // 默认为null，设值后会继续调用的
        mObservableCommodities.value = null

        mRepository = (application as BasicApp).repository

        // 默认加载全部
        // todo 记录用户上次选择标签
        val commodity = mRepository.allCommoditySummary
        mObservableCommodities.addSource<List<CommoditySummaryModel>>(commodity) {
            mObservableCommodities.setValue(it)
        }
    }

//    fun setCommoditiesSummary(place: String) {
//
//        mObservableCommodities.addSource(
//            mRepository.loadCommodityHomeByPlace(
//                place
//            )
//        ) {
//            mObservableCommodities.setValue(it)
//        }
//    }
}
