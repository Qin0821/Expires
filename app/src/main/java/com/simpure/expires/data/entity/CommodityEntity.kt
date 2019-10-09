package com.simpure.expires.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.simpure.expires.data.Converters
import com.simpure.expires.data.ExpiresDate

import com.simpure.expires.data.i.CommodityDetail
import com.simpure.expires.model.Inventory

@Entity(tableName = "commodities")
@TypeConverters(Converters::class)
class CommodityEntity constructor(
    @PrimaryKey
    override var id: Int,
    override var name: String,
    override var productionDate: Long,
    override var expiryDate: Int,
    override val amount: Int = 0,
    override val unit: String = "",
    override val place: String = "",
    override val labels: List<String> = listOf(),
    override val barcode: String = "",
    override val unboxingDuration: Long = 0L,
    override val inventories: List<Inventory> = listOf(),
    override val usedIdList: List<String> = listOf(),
    override val disable: Boolean = false
) : CommodityDetail {

}
