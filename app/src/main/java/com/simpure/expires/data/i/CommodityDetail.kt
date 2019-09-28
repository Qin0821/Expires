package com.simpure.expires.data.i

import com.simpure.expires.model.Inventory

interface CommodityDetail : Commodity {

    val unit: String
    val place: String
    val labels: List<String>?
    val barcode: String
    val inventories: List<Inventory>?
    val amount: Int
    val unboxingDuration: Long
    val usedIdList: List<String>
    val disable: Boolean

}
