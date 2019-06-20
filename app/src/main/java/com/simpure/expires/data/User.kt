package com.simpure.expires.data

data class User(
    val name: String,
    val id: Long,
    val creatTime: Long,
    val vipDuration: Long
)