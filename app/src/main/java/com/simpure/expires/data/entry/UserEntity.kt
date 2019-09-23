package com.simpure.expires.data.entry

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.simpure.expires.data.CommodityType
import com.simpure.expires.data.Converters
import com.simpure.expires.data.User

@Entity(tableName = "user")
@TypeConverters(Converters::class)
class UserEntity(
    // 用户id
    @PrimaryKey var id: Int = 0,
    // 唯一标识
    var uuid: String = "",
    // 用户名
    var name: String = "Guest",
    var token: String = "",
    // 注册时间
    var createTime: Long = 0L,
    // vip剩余时长
    var vipTime: Long = 0L,
    // 免打扰时间
    var notNotifyTime: List<String>? = null,
    // 提醒方式
    var notify: User.NotifyWay = User.NotifyWay(
        app = true,
        email = true,
        message = true
    ),
    // 所在group id列表
    var groupIdList: List<String>? = null,
    // ui风格（正常、黑暗）
    var uiStyle: String = "normal",
    // email
    var email: String = "",
    // 注册手机号
    var phone: Long = 0L,
    // 性别 male female
    var sex: String = "male",
    var age: Int = 0,
    // 头像
    var portrait: String? = null
)

