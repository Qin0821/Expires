package com.simpure.expires;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.simpure.expires.data.AppDatabase;
import com.simpure.expires.data.entry.CommodityEntity;

import java.util.List;

/**
 * Repository handling the work with commodities and comments.
 */
public class DataRepository {

    private static DataRepository sInstance;

    private final AppDatabase mDatabase;
    private MediatorLiveData<List<CommodityEntity>> mObservableProducts;

    private DataRepository(final AppDatabase database) {
        mDatabase = database;
        mObservableProducts = new MediatorLiveData<>();

        mObservableProducts.addSource(mDatabase.commodityDao().loadAllCommodities(),
                productEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableProducts.postValue(productEntities);
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
        return mObservableProducts;
    }

    public LiveData<CommodityEntity> loadCommodities(final int productId) {
        return mDatabase.commodityDao().loadCommodity(productId);
    }

//    public LiveData<List<CommentEntity>> loadComments(final int productId) {
//        return mDatabase.commentDao().loadComments(productId);
//    }
//
//    public LiveData<List<CommodityEntity>> searchCommodities(String query) {
//        return mDatabase.commodityDao().searchAllProducts(query);
//    }
}
