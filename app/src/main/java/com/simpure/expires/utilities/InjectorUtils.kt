/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.simpure.expires.utilities

import android.content.Context
import com.simpure.expires.data.AppDatabase
import com.simpure.expires.data.CommodityRepository
import com.simpure.expires.viewmodels.CommodityHomeViewModel
import com.simpure.expires.viewmodels.CommodityHomeViewModelFactory
import com.simpure.expires.viewmodels.CommodityListViewModel

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {

    private fun getCommodityHomeRepository(context: Context): CommodityRepository {
        return CommodityRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).commodityHomeDao())
    }

    fun provideCommodityHomeViewModelFactory(
        context: Context
    ): CommodityHomeViewModelFactory {
        val repository = getCommodityHomeRepository(context)
        return CommodityHomeViewModelFactory(repository)
    }
}
