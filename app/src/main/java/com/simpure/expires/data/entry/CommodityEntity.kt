package com.simpure.expires.data.entry

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.simpure.expires.data.CommodityType

import com.simpure.expires.model.Commodity
import com.simpure.expires.utilities.calcExpirationDate
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

@Entity(tableName = "commodities")
class CommodityEntity : Commodity {
    @PrimaryKey
    override var id: Int = 0
    override var name: String = ""
    override var expirationDate: Long = 0L
//    override var type: String = calcType()

    override fun calc(): String {
        return expirationDate.calcExpirationDate().toString()
    }

    override fun type(): String {
        val day = calc().toInt()
        return when {
            day < 0 -> CommodityType.EXPIRED
            day <= 10 -> CommodityType.ALMOST
            else -> CommodityType.FRESH
        }
    }

    override fun formatExpirationDate(): String {
        val date = DateTime(expirationDate)
        return date.toString(DateTimeFormat.mediumDate())
    }


    constructor() {}

    @Ignore
    constructor(id: Int, name: String, expirationDate: Long) {
        this.id = id
        this.name = name
        this.expirationDate = expirationDate
    }

    constructor(commodity: Commodity) {
        this.id = commodity.id
        this.name = commodity.name
        this.expirationDate = commodity.expirationDate
    }
}

