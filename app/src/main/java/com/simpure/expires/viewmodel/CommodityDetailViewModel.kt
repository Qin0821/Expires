package com.simpure.expires.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.simpure.expires.BasicApp
import com.simpure.expires.DataRepository
import com.simpure.expires.data.entity.CommodityEntity
import kotlin.properties.Delegates

class CommodityDetailViewModel(application: Application) : AndroidViewModel(application) {

//    private var mCommodityId by Delegates.notNull<Int>()

    private val mRepository: DataRepository = (application as BasicApp).repository

    private val mObservableCommodityDetail: MediatorLiveData<CommodityEntity> =
        MediatorLiveData()

    val commodityDetail: LiveData<CommodityEntity>
        get() = mObservableCommodityDetail


    init {
        mObservableCommodityDetail.value = null
    }


    fun setCommodityId(commodityId: Int) {
        mObservableCommodityDetail.addSource(mRepository.loadCommodity(commodityId)) {
            mObservableCommodityDetail.value = it
        }
    }
//    private final val mObservableCommodity: LiveData<CommodityEntity> =
//        repository.loadCommodity(commodityId)
//    val commodity: ObservableField<CommodityEntity> = ObservableField()
//
//    fun initCommodity(): LiveData<CommodityEntity> = mObservableCommodity
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
