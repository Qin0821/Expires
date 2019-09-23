/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.simpure.expires.data

import com.simpure.expires.data.entry.CommodityEntity
import com.simpure.expires.data.entry.UserEntity
import org.joda.time.DateTime

/**
 * Generates data to pre-populate the database
 */
object DataGenerator {

    fun generateUser(): List<UserEntity> {
        val createTime = DateTime().minusDays(21).millis
        return listOf(
            UserEntity(
                id = 1398762,
                age = 18,
                name = "皮卡婷",
                phone = 15538307252,
                createTime = createTime,
                email = "liuqin0821@gmail.com"
            )
        )
    }

    fun generateCommodities(): List<CommodityEntity> {
        val nowDate = DateTime()

        val fridgeList = arrayListOf(
            CommodityEntity(10, "薯条", nowDate.minusDays(2).millis),
            CommodityEntity(11, "雪碧", nowDate.plusHours(4).millis),
            CommodityEntity(12, "鸡腿", nowDate.plusDays(3).millis),
            CommodityEntity(13, "排骨", nowDate.plusDays(7).millis),
            CommodityEntity(14, "酸奶", nowDate.plusDays(30).millis),
            CommodityEntity(15, "老干妈", nowDate.plusDays(180).millis)
        )

        val makeUpsList = arrayListOf(
            CommodityEntity(20, "dior", nowDate.plusYears(1).millis),
            CommodityEntity(21, "乳液", nowDate.plusMonths(6).millis),
            CommodityEntity(22, "爽肤水", nowDate.plusDays(30).millis),
            CommodityEntity(23, "项链", nowDate.plusYears(100).millis),
            CommodityEntity(24, "手链", nowDate.plusYears(100).millis),
            CommodityEntity(25, "tf", nowDate.plusYears(1).millis)
        )

        val commodityList = fridgeList + makeUpsList

        return commodityList
    }
}
