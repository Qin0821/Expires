package com.simpure.expires.model

data class AccountModel(
    val nickname: String?,
    val email: String?,
    val appleName: String? = null,
    val facebookName: String? = null,
    val twitterName: String? = null,
    val googleName: String? = null,
    val microsoftName: String? = null
)