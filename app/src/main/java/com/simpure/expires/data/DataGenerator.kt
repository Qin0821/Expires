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

    fun generateUser(): UserEntity {
        val createTime = DateTime().minusDays(21).millis
        return UserEntity(
            id = 1398762,
            age = 18,
            name = "皮卡婷",
            phone = 15538307252,
            createTime = createTime,
            email = "liuqin0821@gmail.com"
        )
    }

    fun generateCommodities(): List<CommodityEntity> {
        /*val commodities = ArrayList<CommodityEntity>(FIRST.size * SECOND.size)
        for (i in FIRST.indices) {
            for (j in SECOND.indices) {
                val commodity = CommodityEntity()
                commodity.setName("Hahaha 成功啦")
                commodity.setExpirationDate(3L)
                commodity.setId(0)
                commodities.add(commodity)
            }
        }*/
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

    //    public static List<CommentEntity> generateCommentsForProducts(
    //            final List<CommodityEntity> commodities) {
    //        List<CommentEntity> comments = new ArrayList<>();
    //        Random rnd = new Random();
    //
    //        for (Product product : commodities) {
    //            int commentsNumber = rnd.nextInt(5) + 1;
    //            for (int i = 0; i < commentsNumber; i++) {
    //                CommentEntity comment = new CommentEntity();
    //                comment.setProductId(product.getId());
    //                comment.setText(COMMENTS[i] + " for " + product.getName());
    //                comment.setPostedAt(new Date(System.currentTimeMillis()
    //                        - TimeUnit.DAYS.toMillis(commentsNumber - i) + TimeUnit.HOURS.toMillis(i)));
    //                comments.add(comment);
    //            }
    //        }
    //
    //        return comments;
    //    }
}
