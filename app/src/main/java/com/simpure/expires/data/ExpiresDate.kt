package com.simpure.expires.data

import com.simpure.expires.utilities.calcExpirationDate
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

interface ExpiresDate {
    val productionDate: Long  // 生产日期
    val expiryDate: Int       // 保质日期，xxx天
//    val expirationDate: Long   // 有效期= 生产日期 + 保质期

    /**
     * @return 有效期毫秒值
     */
    fun getExpirationDate(): Long {
        return try {
            val productionDate = DateTime(productionDate)
            productionDate.plusDays(expiryDate)
            productionDate.millis
        } catch (e: Exception) {
            0L
        }
    }

    fun calc(): String {
        return getExpirationDate().calcExpirationDate().toString()
    }

    /**
     * @desc 格式化生产日期
     */
    fun formatProductionDate(): String {
        val productionDate = DateTime(productionDate)
        return productionDate.toString(DateTimeFormat.mediumDate())
    }

    /**
     * @desc 格式化有效期
     */
    fun formatExpirationDate(): String {
        val expirationDate = DateTime(getExpirationDate())
        return expirationDate.toString(DateTimeFormat.mediumDate())
    }

    fun type(): String {
        val day = calc().toInt()
        return when {
            day < 0 -> CommodityType.EXPIRED
            day <= 10 -> CommodityType.ALMOST
            else -> CommodityType.FRESH
        }
    }
}