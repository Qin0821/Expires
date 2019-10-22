package com.simpure.expires.data

import com.simpure.expires.data.dao.CommodityHomeDao
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class CommodityRepository(
    private val commodityHomeDao: CommodityHomeDao
) {
    suspend fun createCommodityHome(id: Long) {
        withContext(IO) {
            val commodity = CommodityHome(id, "name")
        }
    }


    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: CommodityRepository? = null

        fun getInstance(commodityHomeDao: CommodityHomeDao) =
            instance ?: synchronized(this) {
                instance ?: CommodityRepository(commodityHomeDao).also { instance = it }
            }
    }
}

