package com.simpure.expires.model.commodity

data class UserCommodityPageResp(
    val pageNum: Int,
    val pageSize: Int,
    val size: Int,
    val startRow: Int,
    val endRow: Int,
    val total: Int,
    val pages: Int,
    val list: List<CommodityResp>
) {

}
