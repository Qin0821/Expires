package com.simpure.expires.model.user

import com.simpure.expires.model.IBaseResponse

data class LoginResponseI(
    override val code: Int,
    override val msg: String,
    val appUser: AppUserModel,
    val expire: Long,
    val token: String
) : IBaseResponse