package com.simpure.expires.data

data class Commodity(
    val id: Long,
    val name: String,
    val num: Int,
    val unit: String,
    val placeId: Long,      // placeId也是标签的一种，但具有唯一性
    val date: Date? = null,
    val inUse: InUse? = null,
    val stockList: List<Stock>? = null,
    val unboxingDuration: Long = 0,
    val usedList: List<Commodity>? = null,
    val disable: Boolean = false,
    val labelList: List<String>? = null,
    val barcode: String? = null,
    val remindTimeList: List<Long>? = null,
    val depository: Depository? = null
)