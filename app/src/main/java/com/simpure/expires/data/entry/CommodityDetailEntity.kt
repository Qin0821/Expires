package com.simpure.expires.data.entry

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.simpure.expires.data.InUse
import com.simpure.expires.enum.CommodityEnum

import com.simpure.expires.model.Commodity
import com.simpure.expires.model.CommodityDetail
import com.simpure.expires.model.Inventory
import com.simpure.expires.utilities.calcExpirationDate
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

@Entity(tableName = "commodities")
class CommodityDetailEntity : CommodityDetail {
    @PrimaryKey
    override var id: Int = 0
    override val name: String
        get() = ""
    override val expirationDate: Long
        get() = 0
    override val unit: String
        get() = ""
    override val place: String
        get() = ""
    override val labels: List<String>?
        get() = null
    override val barcode: String
        get() = ""
    override val inventories: List<Inventory>?
        get() = null
    override val amount: Int
        get() = 0
    override val inUse: InUse?
        get() = null
    override val unboxingDuration: Long
        get() = 0
    override val usedList: List<CommodityDetail>?
        get() = null
    override val disable: Boolean
        get() = false

    override fun calc(): String {
        return expirationDate.calcExpirationDate().toString()
    }

    override fun type(): CommodityEnum {
        val day = calc().toInt()
        return when {
            day < 0 -> CommodityEnum.EXPIRED
            day <= 10 -> CommodityEnum.ALMOST
            else -> CommodityEnum.FRESH
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

