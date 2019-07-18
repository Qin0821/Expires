package com.simpure.expires.data

data class Commodity(
    val name: String,
    val num: Int,
    val placeId: Long,      // placeId也是标签的一种，但具有唯一性
    val labelList: List<String>,
    val barcode: String?,
    val productionDate: Long,   // 生产日期
    val expiryDate: Int,       // 保质日期，xxx天
    val expirationDate: Long,   // 有效期= 生产日期 + 保质期
    val remindTimeList: List<Long>,
    val depository: Depository
)