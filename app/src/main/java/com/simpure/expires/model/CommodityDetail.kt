package com.simpure.expires.model

import com.simpure.expires.data.*
import com.simpure.expires.enum.CommodityEnum

interface CommodityDetail {

    val unit: String
    val place: String
    val labels: List<String>?
    val barcode: String
    val inventories: List<Inventory>?
    val amount: Int
    val inUse: InUse?
    val unboxingDuration: Long
    val usedList: List<CommodityDetail>?
    val disable: Boolean

    val id: Int
    val name: String
    val expirationDate: Long

    fun calc(): String
    fun type(): String
    fun formatExpirationDate(): String
}
