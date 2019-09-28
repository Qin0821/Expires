package com.simpure.expires.data

data class ExpiresDate(
    val productionDate: Long = 0L,   // 生产日期
    val expiryDate: Int = 0         // 保质日期，xxx天
//    val expirationDate: Long   // 有效期= 生产日期 + 保质期
) {

}