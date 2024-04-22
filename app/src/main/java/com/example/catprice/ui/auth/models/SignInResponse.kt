package com.example.catprice.ui.auth.models

data class SignInResponse(
    val code: Int,
    val success: Boolean,
    val token: String,
    val userData: UserData
)