package com.simpure.expires.model

import com.simpure.expires.enum.CommodityEnum

interface Commodity {
    val id: Int
    val name: String
    val expirationDate: Long
//    val type: String

    fun calc(): String
    fun type(): String
    fun formatExpirationDate(): String
}

