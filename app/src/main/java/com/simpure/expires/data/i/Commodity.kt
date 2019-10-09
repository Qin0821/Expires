package com.simpure.expires.data.i

import com.simpure.expires.data.ExpiresDate

interface Commodity : ExpiresDate {
    val id: Int
    val name: String
}

