package com.simpure.expires.data.entry

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.simpure.expires.data.CommodityType
import com.simpure.expires.data.InUse

import com.simpure.expires.model.Commodity
import com.simpure.expires.model.CommodityDetail
import com.simpure.expires.model.Inventory
import com.simpure.expires.utilities.calcExpirationDate
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

@Entity(tableName = "commodities")
class CommodityEntity @Ignore constructor(
    @PrimaryKey
    override var id: Int, override var name: String, override var expirationDate: Long,
    
) : CommodityDetail {
    override val unit: String
        get() = ""
    override val place: String
        get() = ""
    override val labels: List<String>
        get() = listOf()
    override val barcode: String
        get() = ""
    override val inventories: List<Inventory>
        get() = listOf()
    override val amount: Int
        get() = 0
    override val inUse: InUse
        get() = InUse()
    override val unboxingDuration: Long
        get() = 0L
    override val usedList: List<CommodityDetail>
        get() = listOf()
    override val disable: Boolean
        get() = false

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

}

