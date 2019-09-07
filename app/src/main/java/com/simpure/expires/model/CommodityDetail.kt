package com.simpure.expires.model

import com.simpure.expires.data.*
import com.simpure.expires.data.Commodity

interface CommodityDetail : com.simpure.expires.model.Commodity {

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
}
