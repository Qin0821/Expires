package com.simpure.expires.model

import com.simpure.expires.data.*
import com.simpure.expires.enum.CommodityEnum

interface CommodityDetail : Commodity {

    val unit: String
    val place: String
    val labels: List<String>?
    val barcode: String
    val inventories: List<Inventory>?
    val amount: Int
    val inUse: InUse?
    val unboxingDuration: Long
    val usedIdList: List<String>
    val disable: Boolean

}
