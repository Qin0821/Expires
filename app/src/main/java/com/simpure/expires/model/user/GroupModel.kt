package com.simpure.expires.model.user

data class GroupModel(
    val id: String,
    val newRecord: Boolean,
    val admin: String,
    val name: String
)