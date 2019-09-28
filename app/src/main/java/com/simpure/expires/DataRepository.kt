package com.simpure.expires

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

import com.simpure.expires.data.AppDatabase
import com.simpure.expires.data.entity.CommodityEntity
import com.simpure.expires.data.entity.UserEntity
import com.simpure.expires.model.CommodityModel

/**
 * Repository handling the work with commodities and comments.
 */
class DataRepository private constructor(private val mDatabase: AppDatabase) {
    private val mObservableCommodities: MediatorLiveData<List<CommodityEntity>> = MediatorLiveData()
    private val mObservableCommoditiesSummary: MediatorLiveData<List<CommodityModel>>
    private val mObservableUsers: MediatorLiveData<List<UserEntity>>

    /**
     * Get the list of commodities from the database and get notified when the data changes.
     */
    val commodities: LiveData<List<CommodityEntity>>
        get() = mObservableCommodities

    val commoditiesSummary: LiveData<List<CommodityModel>>
        get() = mObservableCommoditiesSummary

    val allUser: LiveData<List<UserEntity>>
        get() = mObservableUsers

    init {

        mObservableCommodities.addSource(
            mDatabase.commodityDao().loadAllCommodities()
        ) { commodityEntities ->
            if (mDatabase.databaseCreated.value != null) {
                mObservableCommodities.postValue(commodityEntities)
            }
        }

        mObservableCommoditiesSummary = MediatorLiveData()
        mObservableCommoditiesSummary.addSource(
            mDatabase.commodityDao().loadAllCommoditiesSummary()
        ) { commodityModel ->
            if (mDatabase.databaseCreated.value != null) {
                mObservableCommoditiesSummary.postValue(commodityModel)
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
