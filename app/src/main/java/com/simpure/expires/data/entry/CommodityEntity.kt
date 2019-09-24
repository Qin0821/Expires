package com.simpure.expires.data.entry

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.simpure.expires.data.CommodityType
import com.simpure.expires.data.Converters
import com.simpure.expires.data.InUse

import com.simpure.expires.model.CommodityDetail
import com.simpure.expires.model.Inventory
import com.simpure.expires.utilities.calcExpirationDate
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

@Entity(tableName = "commodities")
@TypeConverters(Converters::class)
class CommodityEntity constructor(
    @PrimaryKey
    override var id: Int,
    override var name: String,
    override var expirationDate: Long,
    override val unit: String = "",
    override val place: String = "",
    override val labels: List<String> = listOf(),
    override val barcode: String = "",
    override val inventories: List<Inventory> = listOf(),
    override val amount: Int = 0,
    override val inUse: InUse = InUse(),
    override val usedIdList: List<String> = listOf(),
    override val unboxingDuration: Long = 0L,
    override val disable: Boolean = false
) : CommodityDetail {

}
