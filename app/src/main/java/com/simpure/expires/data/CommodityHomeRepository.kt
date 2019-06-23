package com.simpure.expires.data

data class CommodityHomeRepository(
    val user: User,
    val boxes: List<BoxRepository>
)