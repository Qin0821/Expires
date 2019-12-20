package com.simpure.expires.model.commodity

import com.simpure.expires.data.Commodity
import com.simpure.expires.model.IBaseResponse

data class UserCommodityResponse(
    override val code: Int,
    override val msg: String,
    val data: List<Commodity>
) : IBaseResponse
