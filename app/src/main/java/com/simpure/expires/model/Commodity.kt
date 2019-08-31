package com.simpure.expires.model

import com.simpure.expires.data.*
import com.simpure.expires.data.Commodity

interface Commodity {
    val id: Int
    val name: String
    val expirationDate: Long

    abstract fun calc(): String
    abstract fun type(): CommodityEnum
    abstract fun formatExpirationDate(): String
}

enum class CommodityEnum {
    RED, YELLOW, BLUE,
}