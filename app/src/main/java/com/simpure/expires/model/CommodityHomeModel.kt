package com.simpure.expires.model

data class CommodityHomeModel(
    var placeName: String,
    var commoditySummaryList: List<CommoditySummaryModel>,
    var placeMap: MutableMap<String, List<CommoditySummaryModel>>
)