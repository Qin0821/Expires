package com.simpure.expires.data

import androidx.room.TypeConverter
import com.simpure.expires.model.Inventory
import kotlinx.coroutines.delay
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

    /*@TypeConverter
    fun stringToExpiresDate(value: String): ExpiresDate {
        if (value.isEmpty()) return ExpiresDate()
        val dateArray = value.split(";")
        return ExpiresDate(
            dateArray[0].toLong(),
            dateArray[1].toInt()
        )
    }

    @TypeConverter
    fun expiresDateToString(date: ExpiresDate): String {
        return "${date.productionDate};${date.expiryDate}"
    }*/


    @TypeConverter
    fun stringToList(value: String): List<String>? {
        return value.split("\n")
    }

    @TypeConverter
    fun listToString(list: List<String>?): String {
        if (list === null || list.isEmpty()) return ""

        val str = StringBuilder(list[0])
        list.forEach {
            str.append("\n").append(it)
        }
        return str.toString()
    }

    @TypeConverter
    fun stringToNotifyWay(value: String): User.NotifyWay {
        if (value.isEmpty()) return User.NotifyWay()
        val wayArray = value.split(";")
        return User.NotifyWay(
            wayArray[0].toBoolean(),
            wayArray[1].toBoolean(),
            wayArray[2].toBoolean()
        )
    }

    @TypeConverter
    fun notifyWayToString(way: User.NotifyWay): String {
        return "${way.app};${way.email};${way.message}"
    }

    @TypeConverter
    fun inventoryToString(inventoryList: List<Inventory>): String {
        if (inventoryList.isEmpty()) return ""

        var result = ""
        for (inventory in inventoryList) {
            val value =
                "${inventory.id};${inventory.amount};${inventory.unit};${inventory.barcode};${inventory.productionDate};${inventory.expiryDate}"
            result = if (result.isEmpty()) {
                value
            } else {
                "$result\n$value"
            }
        }
        return result
    }

    @TypeConverter
    fun stringToInventory(value: String): List<Inventory> {
        if (value.isEmpty()) {
            return listOf(Inventory())
        }
        val inventoryStringArray = value.split("\n")
        val result = inventoryStringArray.map {
            val inventoryArray = it.split(";")
            Inventory(
                inventoryArray[0].toInt(),
                inventoryArray[1].toInt(),
                inventoryArray[2],
                inventoryArray[4].toLong(),
                inventoryArray[5].toInt(),
                inventoryArray[3]
            )
        }
        return result
    }

}