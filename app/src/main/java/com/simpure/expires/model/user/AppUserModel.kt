package com.simpure.expires.model.user

data class AppUserModel(
    val id: String,
    val createDate: String,
    val updateDate: String,
    val delFlag: String,
    val newRecord: Boolean,
    val name: String,
    val token: String,
    val vipTime: String,
    val vipLeftTime: Int,
    val email: String,
    val phone: String,
    val sex: Int,
    val age: Int,
    val avator: String,
    val password: String,
    val groupList: List<GroupModel>
)