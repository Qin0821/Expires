package com.simpure.expires.data

import androidx.room.TypeConverter
import com.simpure.expires.model.Inventory
import java.util.Date

/**
 * Type converters to allow Room to reference complex data types.
 * list 用\n隔开，对象用;隔开
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
        return value.split("\n")
    }

    @TypeConverter
    fun listToString(list: List<String>?): String {
        if (list === null) return ""

        val str = StringBuilder(list[0])
        list.forEach {
            str.append("\n").append(it)
        }
        return str.toString()
    }

    @TypeConverter
    fun stringToNotifyWay(value: String): User.NotifyWay {
        val wayArray = value.split(";")
        return User.NotifyWay(
            wayArray[0].toBoolean(),
            wayArray[1].toBoolean(),
            wayArray[2].toBoolean()
        )
    }

    @TypeConverter
    fun notifyWayToString(way: User.NotifyWay): String {
        return "${way.app},${way.email},${way.message}"
    }

    @TypeConverter
    fun inventoryToString(inventoryList: List<Inventory>): String {
        if (inventoryList.isEmpty()) return ""

        var result = ""
        for (inventory in inventoryList) {

            result = "$result\n${inventory.barcode};${inventory.unit};${inventory.amount}"
        }
        return result
    }

    @TypeConverter
    fun stringToInventory(value: String): List<Inventory> {
        val inventoryStringArray = value.split("\n")
        val result = inventoryStringArray.map {
            Inventory(it[0].toString(), it[1].toString(), it[2].toInt())
        }
        return result
    }

    @TypeConverter
    fun inUseToString(inUse: InUse): String {
        return "${inUse.num};${inUse.date};${inUse.unboxingDate}"
    }

    @TypeConverter
    fun stringToInUse(value: String): InUse {
        val inUseStr = value.split(";")
        return InUse(inUseStr[0].toInt(), inUseStr[1].toLong(), inUseStr[2].toLong())
    }
}