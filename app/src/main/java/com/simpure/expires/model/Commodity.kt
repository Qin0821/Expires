package com.simpure.expires.model

import com.simpure.expires.data.CommodityType
import com.simpure.expires.utilities.calcExpirationDate
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

interface Commodity {
    val id: Int
    val name: String
    val expirationDate: Long

    fun calc(): String {
        return expirationDate.calcExpirationDate().toString()
    }

    fun type(): String {
        val day = calc().toInt()
        return when {
            day < 0 -> CommodityType.EXPIRED
            day <= 10 -> CommodityType.ALMOST
            else -> CommodityType.FRESH
        }
    }

    fun formatExpirationDate(): String {
        val date = DateTime(expirationDate)
        return date.toString(DateTimeFormat.mediumDate())
    }
}

