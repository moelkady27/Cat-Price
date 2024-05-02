package com.example.catprice.ui.setting.models

data class UserResponse(
    val code: Int,
    val success: Boolean,
    val userData: UserData
)