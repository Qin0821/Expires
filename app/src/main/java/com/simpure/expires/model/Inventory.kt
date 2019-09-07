package com.simpure.expires.model

interface Inventory : Commodity {

    val barcode: String
    val unit: String
    val amount: Int

}