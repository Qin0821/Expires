package com.simpure.expires

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

import com.simpure.expires.data.AppDatabase
import com.simpure.expires.data.entity.CommodityEntity
import com.simpure.expires.data.entity.GroupEntity
import com.simpure.expires.data.entity.UserEntity
import com.simpure.expires.model.CommoditySummaryModel

/**
 * Repository handling the work with commodities and comments.
 */
class DataRepository private constructor(private val mDatabase: AppDatabase) {
    private val mObservableCommodities: MediatorLiveData<List<CommodityEntity>> = MediatorLiveData()
    private val mObservableCommoditiesSummary: MediatorLiveData<List<CommoditySummaryModel>> =
        MediatorLiveData()
    private val mObservableUsers: MediatorLiveData<List<UserEntity>> = MediatorLiveData()
    private val mObservableGroups: MediatorLiveData<List<GroupEntity>> = MediatorLiveData()

    /**
     * Get the list of commodities from the database and get notified when the data changes.
     */
    val commodities: LiveData<List<CommodityEntity>>
        get() = mObservableCommodities

    val allCommoditySummary: LiveData<List<CommoditySummaryModel>>
        get() = mObservableCommoditiesSummary

    val allUser: LiveData<List<UserEntity>>
        get() = mObservableUsers

    val allGroup: LiveData<List<GroupEntity>>
        get() = mObservableGroups

    init {

        // no used
        /*mObservableCommodities.addSource(
            mDatabase.commodityDao().loadAllCommodities()
        ) { commodityEntities ->
            if (mDatabase.databaseCreated.value != null) {
                mObservableCommodities.postValue(commodityEntities)
            }
        }*/

        mObservableCommoditiesSummary.addSource(
            mDatabase.commodityDao().loadAllCommoditiesSummary()
        ) { commoditySummaryList ->
            if (mDatabase.databaseCreated.value != null) {
                mObservableCommoditiesSummary.postValue(commoditySummaryList)
            }
        }

        mObservableUsers.addSource(
            mDatabase.userDao().getAllUser()
        ) { userEntities ->
            if (mDatabase.databaseCreated.value != null) {
                mObservableUsers.postValue(userEntities)
            }
        }


//        val dao = mDatabase.groupDao().getAllGroup()
//        mObservableGroups.addSource(
//            dao
//        ) { userEntities ->
//            if (mDatabase.databaseCreated.value != null) {
//                mObservableGroups.postValue(userEntities)
//            }
//        }
    }

    fun loadUserById(id: Int): LiveData<UserEntity> {
        return mDatabase.userDao().loadUserByIds(id)
    }

//    fun loadGroupById(id: String): LiveData<GroupEntity> {
//        return mDatabase.groupDao().loadGroupById(id)
//    }

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
