package com.simpure.expires.data.i

import com.simpure.expires.data.CommodityType
import com.simpure.expires.data.ExpiresDate
import com.simpure.expires.utilities.calcExpirationDate
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

interface Commodity {
    val id: Int
    val name: String
    val date: ExpiresDate

    fun calc(): String {
        return getExpirationDate().calcExpirationDate().toString()
    }

    fun type(): String {
        val day = calc().toInt()
        return when {
            day < 0 -> CommodityType.EXPIRED
            day <= 10 -> CommodityType.ALMOST
            else -> CommodityType.FRESH
        }
    }

    /**
     * @desc 格式化生产日期
     */
    fun formatProductionDate(): String {
        val productionDate = DateTime(date.productionDate)
        return productionDate.toString(DateTimeFormat.mediumDate())
    }

    /**
     * @return 有效期毫秒值
     */
    fun getExpirationDate(): Long {
        return try {
            val productionDate = DateTime(date.productionDate)
            productionDate.plusDays(date.expiryDate.toInt())
            productionDate.millis
        } catch (e: Exception) {
            0L
        }
    }

    /**
     * @desc 格式化有效期
     */
    fun formatExpirationDate(): String {
        val expirationDate = DateTime(getExpirationDate())
        return expirationDate.toString(DateTimeFormat.mediumDate())
    }
}

