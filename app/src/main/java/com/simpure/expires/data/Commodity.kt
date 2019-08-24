package com.simpure.expires.data

data class Commodity(
    val id: Long,
    val unit: String,
    val name: String,
    val num: Int,
    val inUse: InUse?,
    val stockList: List<Stock>?,
    val unboxingDuration: Long,
    val usedList: List<Commodity>?,
    val disable: Boolean,
    val placeId: Long,      // placeId也是标签的一种，但具有唯一性
    val date: Date?,
    val labelList: List<String>? = null,
    val barcode: String? = null,
    val remindTimeList: List<Long>? = null,
    val depository: Depository? = null
)