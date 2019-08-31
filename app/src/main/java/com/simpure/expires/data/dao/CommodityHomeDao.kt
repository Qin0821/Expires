package com.simpure.expires.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.simpure.expires.data.entry.CommodityEntity

/**
 * THe Data Access Object for the CommodityHome class.
 */
@Dao
interface CommodityHomeDao {

//    @Query("SELECT * FROM commodity_home WHERE id = :userId")
//    fun getCommodityList(userId: String): LiveData<List<CommodityEntity>>
}