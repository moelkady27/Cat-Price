package com.example.catprice.ui.auth.request

data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String,
    val country: String,
    val phoneNumber: String,
    val type: String
)
