package com.simpure.expires.model

import com.simpure.expires.data.ExpiresDate
import com.simpure.expires.data.i.Commodity

class CommodityModel(
    override val id: Int,
    override val name: String,
    override val productionDate: Long,
    override val expiryDate: Int
) : Commodity