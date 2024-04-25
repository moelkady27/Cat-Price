package com.example.catprice.ui.setting.repository

import com.example.catprice.retrofit.ApiService
import com.example.catprice.ui.setting.models.ChangePasswordResponse
import com.example.catprice.ui.setting.request.ChangePasswordRequest
import retrofit2.Call

class ChangePasswordRepository(
    private val apiService: ApiService
) {
    fun changePass(
        token: String,
        currentPassword: String,
        newPassword: String,
        confirmPassword: String,
        userId: String
    ) : Call<ChangePasswordResponse>{
        val data = ChangePasswordRequest(currentPassword, newPassword, confirmPassword)
        return apiService.changePass("Bearer $token", data, userId)
    }
}