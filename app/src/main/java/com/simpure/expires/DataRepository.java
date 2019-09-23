package com.simpure.expires;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.simpure.expires.data.AppDatabase;
import com.simpure.expires.data.entry.CommodityEntity;
import com.simpure.expires.data.entry.UserEntity;

import java.util.List;

/**
 * Repository handling the work with commodities and comments.
 */
public class DataRepository {

    private static DataRepository sInstance;

    private final AppDatabase mDatabase;
    private MediatorLiveData<List<CommodityEntity>> mObservableCommodities;
    private MediatorLiveData<List<UserEntity>> mObservableUsers;

    private DataRepository(final AppDatabase database) {
        mDatabase = database;
        mObservableCommodities = new MediatorLiveData<>();
        mObservableCommodities.addSource(mDatabase.commodityDao().loadAllCommodities(),
                commodityEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableCommodities.postValue(commodityEntities);
                    }
                });

        mObservableUsers = new MediatorLiveData<>();
        mObservableUsers.addSource(mDatabase.userDao().getAllUser(),
                userEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableUsers.postValue(userEntities);
                    }
                });
    }

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of commodities from the database and get notified when the data changes.
     */
    public LiveData<List<CommodityEntity>> getCommodities() {
        return mObservableCommodities;
    }

    public LiveData<List<UserEntity>> getAllUser() {
        return mObservableUsers;
    }

    public LiveData<CommodityEntity> loadCommodities(final int productId) {
        return mDatabase.commodityDao().loadCommodity(productId);
    }

    public LiveData<UserEntity> loadUser(int userId) {
        return mDatabase.userDao().loadByIds(userId);
    }
//    public LiveData<List<CommentEntity>> loadComments(final int productId) {
//        return mDatabase.commentDao().loadComments(productId);
//    }
//
//    public LiveData<List<CommodityEntity>> searchCommodities(String query) {
//        return mDatabase.commodityDao().searchAllProducts(query);
//    }
}
