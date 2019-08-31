package com.simpure.expires.data

data class Depository(
    val name: String,   // ex：name：蒙牛，depositoryName： 伊利
    val num: Int,
    val barcode: String,
    val productionDate: Long,   // 生产日期
    val expiryDate: Int,       // 保质日期，xxx天
    val expirationDate: Long   // 有效期= 生产日期 + 保质期
)