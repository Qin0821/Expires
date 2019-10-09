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

import com.simpure.expires.data.entity.CommodityEntity
import com.simpure.expires.data.entity.UserEntity
import com.simpure.expires.model.Inventory
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
            CommodityEntity(
                10,
                "薯条",
                ExpiresDate(nowDate.minusDays(7).millis, 3),
                20,
                "根",
                "fridge",
                listOf("food", "delicious", "yellow"),
                "6955725010589",
                DateTime.now().millis,
                listOf(Inventory("WYT5201314", "根", 52))
            ),
            CommodityEntity(11, "雪碧", ExpiresDate(nowDate.minusDays(15).millis, 15)),
            CommodityEntity(12, "鸡腿", ExpiresDate(nowDate.minusDays(15).millis, 18)),
            CommodityEntity(13, "排骨", ExpiresDate(nowDate.minusDays(0).millis, 7)),
            CommodityEntity(14, "酸奶", ExpiresDate(nowDate.minusDays(160).millis, 180)),
            CommodityEntity(15, "老干妈", ExpiresDate(nowDate.minusDays(60).millis, 180))
        )

        val makeUpsList = arrayListOf(
            CommodityEntity(20, "dior", ExpiresDate(nowDate.minusDays(60).millis, 180)),
            CommodityEntity(21, "乳液", ExpiresDate(nowDate.minusDays(60).millis, 180)),
            CommodityEntity(22, "爽肤水", ExpiresDate(nowDate.minusDays(60).millis, 180)),
            CommodityEntity(23, "项链", ExpiresDate(nowDate.minusDays(60).millis, 180)),
            CommodityEntity(24, "手链", ExpiresDate(nowDate.minusDays(60).millis, 180)),
            CommodityEntity(25, "tf", ExpiresDate(nowDate.minusDays(60).millis, 180))
        )

        val commodityList = fridgeList + makeUpsList

        return commodityList
    }
}
