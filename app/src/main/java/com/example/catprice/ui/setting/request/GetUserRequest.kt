package com.example.catprice.ui.setting.request

data class GetUserRequest(
//    val appDiscount: Int,
    val name: String,
    val phoneNumber: String,
    val email: String,
    val type: String
)
