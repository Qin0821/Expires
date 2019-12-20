package com.simpure.expires.model.user

import com.simpure.expires.model.IBaseResponse

data class UserInfoResponseI(
    override val code: Int,
    override val msg: String,
    val appUser: AppUserModel
) : IBaseResponse