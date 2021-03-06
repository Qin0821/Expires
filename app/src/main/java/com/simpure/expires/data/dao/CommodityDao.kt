/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.simpure.expires.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.simpure.expires.data.entity.CommodityEntity
import com.simpure.expires.model.CommoditySummaryModel

@Dao
interface CommodityDao {
    @Query("SELECT * FROM commodities")
    fun loadAllCommodities(): LiveData<List<CommodityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(commodities: List<CommodityEntity>)

    @Query("SELECT * FROM commodities WHERE id = :commodityId")
    fun loadCommodity(commodityId: Int): LiveData<CommodityEntity>

    @Query("SELECT * FROM commodities WHERE id = :commodityId")
    fun loadCommoditySync(commodityId: Int): CommodityEntity

    @Query("SELECT id, name, productionDate, expiryDate FROM commodities")
    fun loadAllCommoditiesSummary(): LiveData<List<CommoditySummaryModel>>

//    @Query("SELECT id, name, productionDate, expiryDate, place FROM commodities WHERE place LIKE :place")
//    @Query("SELECT id, name, productionDate, expiryDate FROM commodities where place = :place")
//    fun loadCommoditiesSummaryByName(place: String): LiveData<List<CommoditySummaryModel>>

    //    @Query("SELECT commodities.* FROM commodities JOIN commoditiesFts ON (commodities.id = commoditiesFts.rowid) "
    //        + "WHERE commoditiesFts MATCH :query")
    //    LiveData<List<CommodityEntity>> searchAllProducts(String query);
}
