package com.simpure.expires.data

data class BoxRepository(
    var boxName: String,
    var commodityList: List<CommodityRepository>
)