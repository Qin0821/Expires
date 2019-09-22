package com.simpure.expires.data

import androidx.room.TypeConverter
import java.util.Date

/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return if (timestamp == null) null else Date(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }


    @TypeConverter
    fun stringToList(value: String): List<String>? {
        return value.split(",")
    }

    @TypeConverter
    fun listToString(list: List<String>?): String {
        if (list === null) return ""

        val str = StringBuilder(list[0])
        list.forEach {
            str.append(",").append(it)
        }
        return str.toString()
    }

    @TypeConverter
    fun stringToNotifyWay(value: String): User.NotifyWay {
        val wayList = value.split(",")
        return User.NotifyWay(
            wayList[0].toBoolean(),
            wayList[1].toBoolean(),
            wayList[2].toBoolean()
        )
    }

    @TypeConverter
    fun notifyWayToString(way: User.NotifyWay): String {
        return "${way.app},${way.email},${way.message}"
    }
}