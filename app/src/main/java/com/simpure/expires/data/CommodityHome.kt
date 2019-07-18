package com.simpure.expires.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "commodity_home"
)
data class CommodityHome(
    @PrimaryKey @ColumnInfo(name = "id") val userId: Long,
    val name: String
) {

    override fun toString(): String {
        return name
    }
}