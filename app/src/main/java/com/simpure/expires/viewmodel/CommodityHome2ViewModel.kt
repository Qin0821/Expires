package com.simpure.expires.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.simpure.expires.BasicApp
import com.simpure.expires.DataRepository
import com.simpure.expires.model.CommodityHomeModel
import com.simpure.expires.ui.CommodityHomeActivity
import kotlin.properties.Delegates

/**
 * The ViewModel for [CommodityHomeActivity]
 */
class CommodityHome2ViewModel(application: Application) : AndroidViewModel(application) {

//    private var mPlaceName by Delegates.notNull<String>()

    private val mRepository: DataRepository = (application as BasicApp).repository

    private val mObservableCommodityHome: MediatorLiveData<CommodityHomeModel> = MediatorLiveData()

    val commodityHome: LiveData<CommodityHomeModel>
        get() = mObservableCommodityHome


    init {
        mObservableCommodityHome.value = null
    }

    fun setPlaceName(placeName: String) {
        mObservableCommodityHome.addSource(mRepository.loadCommodityHomeByPlace(placeName)) {
            mObservableCommodityHome.value = CommodityHomeModel(placeName, it)
        }
    }

}