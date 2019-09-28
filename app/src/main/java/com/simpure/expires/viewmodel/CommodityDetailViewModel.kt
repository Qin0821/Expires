package com.simpure.expires.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.simpure.expires.BasicApp
import com.simpure.expires.DataRepository
import com.simpure.expires.data.entity.CommodityEntity

class CommodityDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: DataRepository

    private val mObservableCommodityDetail: MediatorLiveData<List<CommodityEntity>> =
        MediatorLiveData()

    val commodityDetail: LiveData<List<CommodityEntity>>
        get() = mObservableCommodityDetail

    init {
        mObservableCommodityDetail.value = null

        mRepository = (application as BasicApp).repository

        val commodityDetailList = mRepository.commodities
        mObservableCommodityDetail.addSource(commodityDetailList) {

        }
    }

//    private final val mObservableCommodity: LiveData<CommodityEntity> =
//        repository.loadCommodity(commodityId)
//    val commodity: ObservableField<CommodityEntity> = ObservableField()
//
//    fun getCommodity(): LiveData<CommodityEntity> = mObservableCommodity
//
//    fun setCommodity(commodity: CommodityEntity) {
//        this.commodity.set(commodity)
//    }
//
//    class Factory(private val application: Application, private val commodityId: Int) :
//        ViewModelProvider.NewInstanceFactory() {
//
//        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//            return CommodityDetailViewModel(
//                application,
//                (application as BasicApp).repository,
//                commodityId
//            ) as T
//        }
//    }
}
