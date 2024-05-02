package com.example.catprice.ui.setting.repository

import com.example.catprice.retrofit.ApiService
import com.example.catprice.ui.setting.models.UserResponse
import com.example.catprice.ui.setting.request.GetUserRequest
import retrofit2.Call

class ProfileRepository(
    private val apiService: ApiService
) {
    fun getUser(
        token: String,
        userId: String
    ): Call<UserResponse> {
        return apiService.getUser("Bearer $token", userId)
    }

    fun updateUser(
        token: String,
        userId: String,
        name: String,
        phoneNumber: String,
        email: String,
        type: String
    ): Call<UserResponse> {
        val data = GetUserRequest(name, phoneNumber, email, type)
        return apiService.updateUser("Bearer $token", userId, data)
    }
}