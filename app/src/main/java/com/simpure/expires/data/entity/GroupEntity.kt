package com.simpure.expires.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.simpure.expires.data.Converters

@Entity(tableName = "group")
@TypeConverters(Converters::class)
class GroupEntity(
    // group id
    @PrimaryKey var id: String = "",
    // 成员id列表
    var userIdList: List<String> = listOf(),
    // 拥有的place id列表
    var placeList: List<String> = listOf(),
    // 群主
    var ownerId: Int = 0,
    // 管理员
    var adminList: List<String> = listOf(),
    // 同步开关
    var syncSwitch: Boolean = true
)