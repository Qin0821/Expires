package com.simpure.expires.data

/**
 * The top level
 */
data class User(
    val id: Long,
    val token: String,
    val name: String,
    val avatar: String,
    val sex: String, //male\female
    val age: Int,
    val email: String,
    val phone: Long,
    val uuid: String,
    val theme: String, // dark\light
    val isSync: Boolean, //同步开关
    val notifyWay: NotifyWay,
    val groupList: List<Group>,

    val vipTime: Long,
    val createTime: Long,
    val quietTime: String  // 免打扰时间
) {
    data class NotifyWay(
        val app: Boolean = true,
        val email: Boolean = true,
        val message: Boolean = true
    ) {

    }
}