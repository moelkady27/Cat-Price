package com.example.catprice.ui.auth.repository

import com.example.catprice.retrofit.ApiService
import com.example.catprice.ui.auth.models.SignUpResponse
import com.example.catprice.ui.auth.request.SignUpRequest
import retrofit2.Call

class SignUpRepository(
    private val apiService: ApiService
) {
    fun signUp(
        name: String,
        email: String,
        password: String,
        country: String,
        phoneNumber: String,
        type: String
    ): Call<SignUpResponse> {
        val data = SignUpRequest(name, email, password, country, phoneNumber, type)
        return apiService.register(data)
    }
}