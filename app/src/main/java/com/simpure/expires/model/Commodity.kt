package com.simpure.expires.model

interface Commodity {
    val id: Int
    val name: String
    val expirationDate: Long

    fun calc(): String
    fun type(): String
    fun formatExpirationDate(): String
}

