package com.example.catprice.ui.auth.models

data class LogOutResponse(
    val code: Int,
    val message: String,
    val success: Boolean
)