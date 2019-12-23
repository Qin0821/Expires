package com.simpure.expires.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.simpure.expires.BasicApp
import com.simpure.expires.DataRepository
import com.simpure.expires.model.CommodityHomeModel
import com.simpure.expires.ui.home.CommodityHomeActivity

/**
 * The ViewModel for [CommodityHomeActivity]
 */
class CommodityHome2ViewModel(application: Application) : AndroidViewModel(application) {

//    private var mPlaceName by Delegates.notNull<String>()

    private val mRepository: DataRepository = (application as BasicApp).repository

    private val mObservableCommodityHome: MediatorLiveData<CommodityHomeModel> = MediatorLiveData()

    val commodityHome: LiveData<CommodityHomeModel>
        get() = mObservableCommodityHome

    private lateinit var mCommodityHomeModel: CommodityHomeModel

    init {
        mObservableCommodityHome.value = null
        mObservableCommodityHome.addSource(mRepository.allCommoditySummary) {
            mCommodityHomeModel = CommodityHomeModel("All", it, mutableMapOf("All" to it))
            mObservableCommodityHome.value = mCommodityHomeModel
        }
    }

    fun setPlaceName(place: String) {

        if (place == "All") {
            mCommodityHomeModel.apply {
                placeName = "All"
                commoditySummaryList = placeMap["All"]!!
            }
            mObservableCommodityHome.value = mCommodityHomeModel
        } else {
            mObservableCommodityHome.addSource(mRepository.loadCommodityHomeByPlace(place)) {
                mCommodityHomeModel.apply {
                    placeName = place
                    commoditySummaryList = it
                    placeMap[place] = it
                }
                mObservableCommodityHome.value = mCommodityHomeModel
            }
        }
    }

}