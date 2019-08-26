package com.simpure.expires.model

import com.simpure.expires.data.*
import com.simpure.expires.data.Commodity

interface Commodity {
    val id: Int
    val name: String
    val date: Long?
}