package com.simpure.expires

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

import com.simpure.expires.data.AppDatabase
import com.simpure.expires.data.entity.CommodityEntity
import com.simpure.expires.data.entity.UserEntity
import com.simpure.expires.model.CommodityHomeModel
import com.simpure.expires.model.CommoditySummaryModel

/**
 * Repository handling the work with commodities and comments.
 */
class DataRepository private constructor(private val mDatabase: AppDatabase) {
    private val mObservableCommodities: MediatorLiveData<List<CommodityEntity>> = MediatorLiveData()
    private val mObservableCommoditiesSummary: MediatorLiveData<List<CommoditySummaryModel>>
    private val mObservableUsers: MediatorLiveData<List<UserEntity>>

    /**
     * Get the list of commodities from the database and get notified when the data changes.
     */
    val commodities: LiveData<List<CommodityEntity>>
        get() = mObservableCommodities

    val allCommoditySummary: LiveData<List<CommoditySummaryModel>>
        get() = mObservableCommoditiesSummary

    val allUser: LiveData<List<UserEntity>>
        get() = mObservableUsers

    init {

        // no used
        /*mObservableCommodities.addSource(
            mDatabase.commodityDao().loadAllCommodities()
        ) { commodityEntities ->
            if (mDatabase.databaseCreated.value != null) {
                mObservableCommodities.postValue(commodityEntities)
            }
        }*/

        mObservableCommoditiesSummary = MediatorLiveData()
        mObservableCommoditiesSummary.addSource(
            mDatabase.commodityDao().loadAllCommoditiesSummary()
        ) { commoditySummaryList ->
            if (mDatabase.databaseCreated.value != null) {
                mObservableCommoditiesSummary.postValue(commoditySummaryList)
            }
        }

        mObservableUsers = MediatorLiveData()
        mObservableUsers.addSource(
            mDatabase.userDao().getAllUser()
        ) { userEntities ->
            if (mDatabase.databaseCreated.value != null) {
                mObservableUsers.postValue(userEntities)
            }
        }
    }

    fun loadCommodity(commodityId: Int): LiveData<CommodityEntity> {
        return mDatabase.commodityDao().loadCommodity(commodityId)
    }

    fun loadCommodityHomeByPlace(place: String): LiveData<List<CommoditySummaryModel>> {
        return mDatabase.commodityDao().loadCommoditiesSummaryByName(place)
    }

    companion object {

        private var sInstance: DataRepository? = null

        fun getInstance(database: AppDatabase): DataRepository {
            if (sInstance == null) {
                synchronized(DataRepository::class.java) {
                    if (sInstance == null) {
                        sInstance = DataRepository(database)
                    }
                }
            }
            return sInstance!!
        }
    }

}
