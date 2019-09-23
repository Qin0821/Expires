package com.simpure.expires.data.entry

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.simpure.expires.data.Converters

@Entity(tableName = "group")
@TypeConverters(Converters::class)
class GroupEntity(
    // group id
    @PrimaryKey var id: Int = 0,
    // 成员id列表
    var userIdList: List<String> = listOf(),
    // 拥有的place id列表
    var placeIdList: List<String>? = null,
    // 群主
    var ownerId: Int = 0,
    // 管理员
    var adminId: Int = 0,
    // 同步开关
    var syncSwitch: Boolean = true
)