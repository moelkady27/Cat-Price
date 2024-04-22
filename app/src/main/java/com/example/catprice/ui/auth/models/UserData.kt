package com.example.catprice.ui.auth.models

data class UserData(
    val __v: Int,
    val _id: String,
    val appDiscount: Int,
    val au: Int,
    val companyName: String,
    val country: String,
    val countryDiscount: Int,
    val devicesCount: Int,
    val email: String,
    val favListId: Any,
    val hiddenDiscount: Int,
    val isActive: Boolean,
    val name: String,
    val numOfUsers: Int,
    val pd: Int,
    val phoneNumber: String,
    val plan: Plan,
    val pt: Int,
    val rh: Int,
    val role: String,
    val type: String
)