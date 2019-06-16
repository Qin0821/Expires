package com.simpure.expires.data

data class CommodityRepository(
    var name: String,
    var barCode: String?,
    var produceDate: Long,
    var expiresTime: Long,
    var remindDay: Int,
    var type: CommodityEnum
)

