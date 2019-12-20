package com.simpure.expires.model.commodity

data class ExCommodityEntity(
    val barcode: String,
    val depositoryid: String,
    val id: String? = null,
    val labels: String,
    val name: String,
    val pageNum: Int? = null,
    val pageSize: Int? = null,
    val place: String,
    val remindtimelist: String,
    val sqlMap: String? = null,
    val unit: String
)
