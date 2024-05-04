package com.example.catprice.ui.setting.repository

import com.example.catprice.retrofit.ApiService
import com.example.catprice.ui.setting.models.ContactUsResponse
import com.example.catprice.ui.setting.request.ContactUsRequest
import retrofit2.Call

class ContactUsRepository(
    private val apiService: ApiService
) {
    fun contactUs(
        token: String,
        userId: String,
        message: String
    ): Call<ContactUsResponse> {
        val data = ContactUsRequest(userId, message)
        return apiService.contactUs("Bearer $token", data)
    }
}