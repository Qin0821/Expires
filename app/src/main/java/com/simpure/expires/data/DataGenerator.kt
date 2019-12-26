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
import com.simpure.expires.data.entity.GroupEntity
import com.simpure.expires.data.entity.UserEntity
import com.simpure.expires.model.Inventory
import org.joda.time.DateTime

/**
 * Generates data to pre-populate the database
 * !!! 更改该文件需清楚app数据才生效 !!!
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
                email = "liuqin0821@gmail.com",
                groupIdList = listOf("g001", "g002", "g003")
            )
        )
    }

    fun generateGroup(): List<GroupEntity> {
        return listOf(
            GroupEntity(
                "g001",
                listOf("1398762", "1398763", "1398764"),
                listOf("Fridge", "makeUpsList", "Snacks"),
                1398762,
                listOf("1398762", "1398763"),
                true
            )
        )
    }

    fun generateCommodities(): List<CommodityEntity> {
        val nowDate = DateTime()

        val fridgeList = arrayListOf(
            CommodityEntity(
                10,
                "薯条",
                nowDate.minusDays(7).millis,
                3,
                20,
                "根",
                "Fridge",
                listOf("food", "delicious", "yellow"),
                "6955725010589",
                DateTime.now().millis,
                listOf(
                    Inventory(
                        101,
                        52,
                        "根",
                        nowDate.minusDays(2).millis,
                        3,
                        "WYT5201314"
                    ),
                    Inventory(
                        102,
                        30,
                        "根",
                        nowDate.minusDays(1).millis,
                        3,
                        "WYT5201314"
                    ),
                    Inventory(
                        103,
                        40,
                        "根",
                        nowDate.millis,
                        3,
                        "WYT5201314"
                    )
                )
            ),
            CommodityEntity(11, "雪碧", nowDate.minusDays(15).millis, 15, 2, "瓶", "Fridge"),
            CommodityEntity(12, "鱼🐟", nowDate.minusDays(2).millis, 7, 1, place = "Fridge"),
            CommodityEntity(13, "排骨", nowDate.minusDays(0).millis, 7, place = "Fridge"),
            CommodityEntity(14, "酸奶", nowDate.minusDays(160).millis, 180, place = "Fridge"),
            CommodityEntity(15, "老干妈", nowDate.minusDays(60).millis, 180, place = "Fridge")
        )

        val makeUpsList = arrayListOf(
            CommodityEntity(
                20,
                "dior",
                nowDate.minusDays(60).millis,
                180,
                1,
                "支",
                "makeUpsList",
                listOf("beautiful"),
                "6927033139320",
                DateTime.now().minusDays(1).millis,
                listOf(
                    Inventory(
                        201,
                        2,
                        "支",
                        nowDate.minusDays(2).millis,
                        180,
                        "WYT5201314"
                    ),
                    Inventory(
                        202,
                        3,
                        "支",
                        nowDate.minusDays(1).millis,
                        180,
                        "WYT5201314"
                    ),
                    Inventory(
                        203,
                        4,
                        "支",
                        nowDate.millis,
                        180,
                        "WYT5201314"
                    )
                )
            ),
            CommodityEntity(21, "乳液", nowDate.minusDays(60).millis, 180, place = "makeUpsList"),
            CommodityEntity(22, "爽肤水", nowDate.minusDays(60).millis, 180, place = "makeUpsList"),
            CommodityEntity(23, "项链", nowDate.minusDays(60).millis, 180, place = "makeUpsList"),
            CommodityEntity(24, "手链", nowDate.minusDays(60).millis, 180, place = "makeUpsList"),
            CommodityEntity(25, "tf", nowDate.minusDays(60).millis, 180, place = "makeUpsList")
        )

        val snacksList = arrayListOf(
            CommodityEntity(
                30,
                "炸鸡",
                nowDate.minusHours(2).millis,
                1,
                10,
                "块",
                "Snacks",
                listOf("food", "delicious", "yellow"),
                "4987074071076",
                DateTime.now().millis,
                listOf(
                    Inventory(
                        301,
                        12,
                        "块",
                        nowDate.minusHours(1).millis,
                        1,
                        "WYT5201314"
                    ),
                    Inventory(
                        302,
                        13,
                        "块",
                        nowDate.millis,
                        1,
                        "WYT5201314"
                    ),
                    Inventory(
                        303,
                        14,
                        "块",
                        nowDate.millis,
                        1,
                        "WYT5201314"
                    )
                )
            ),
            CommodityEntity(31, "虾条", nowDate.minusMinutes(2).millis, 1, place = "Snacks"),
            CommodityEntity(32, "羊肉串", nowDate.minusMinutes(4).millis, 1, place = "Snacks"),
            CommodityEntity(33, "烤茄子", nowDate.minusMinutes(6).millis, 1, place = "Snacks"),
            CommodityEntity(34, "烤生蚝", nowDate.minusMinutes(8).millis, 1, place = "Snacks"),
            CommodityEntity(35, "烤面筋", nowDate.minusMinutes(10).millis, 1, place = "Snacks")
        )

        val commodityList = fridgeList + makeUpsList + snacksList + fridgeList + makeUpsList + snacksList

        return commodityList
    }
}
