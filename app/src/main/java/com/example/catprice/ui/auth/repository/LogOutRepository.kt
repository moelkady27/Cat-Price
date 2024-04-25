package com.example.catprice.ui.auth.repository

import com.example.catprice.retrofit.ApiService
import com.example.catprice.ui.auth.models.LogOutResponse
import retrofit2.Call

class LogOutRepository(
    private val apiService: ApiService
) {
    fun logOut(
        token: String,
        userId: String
    ): Call<LogOutResponse> {
        return apiService.logOut("Bearer $token", userId)
    }
}