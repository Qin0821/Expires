package com.simpure.expires.model

data class ApiResponse<T>(
    val code: Int,
    val message: String = "",
    val data: T? = null
)