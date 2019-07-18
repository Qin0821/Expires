package com.simpure.expires.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

/**
 * THe Data Access Object for the CommodityHome class.
 */
@Dao
interface CommodityHomeDao {
    @Query("SELECT * FROM commodity_home ORDER BY name ")
    fun getName(): LiveData<String>

    @Query("SELECT * FROM commodity_home WHERE id = :userId")
    fun getCommodityList(userId: String)
}