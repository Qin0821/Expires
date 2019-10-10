package com.simpure.expires.model

import com.simpure.expires.data.ExpiresDate

data class Inventory(
    val id: Int = 0,
    val amount: Int = 0,
    val unit: String = "",
    override var productionDate: Long = 0L,
    override var expiryDate: Int = 0,
    val barcode: String = ""
) : ExpiresDate {

}