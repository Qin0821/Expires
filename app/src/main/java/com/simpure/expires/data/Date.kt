package com.simpure.expires.data

data class Date(
    val productionDate: Long,   // 生产日期
    val expiryDate: Int,       // 保质日期，xxx天
    val expirationDate: Long   // 有效期= 生产日期 + 保质期
)