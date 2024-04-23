package com.example.catprice.ui.setting.models

data class ChangePasswordResponse(
    val code: Int,
    val message: String,
    val success: Boolean
)