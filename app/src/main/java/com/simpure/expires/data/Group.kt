package com.simpure.expires.data

/**
 * 一个Group只有一个owner，可存在多个admin和user
 */
data class Group(
    val id: Long,
    val ownerId: Long,
    val userIdList: List<Long>,
    val adminIdList: List<Long>, // contain owner
    val placeId: List<Long>
)