package com.simpure.expires.model

import com.simpure.expires.model.IBaseResponse

data class BaseResponse(override val code: Int, override val msg: String) : IBaseResponse