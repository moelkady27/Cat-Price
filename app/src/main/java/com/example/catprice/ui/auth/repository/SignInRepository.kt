package com.example.catprice.ui.auth.repository

import com.example.catprice.retrofit.ApiService
import com.example.catprice.ui.auth.request.SignInRequest
import com.example.catprice.ui.auth.models.SignInResponse
import retrofit2.Call

class SignInRepository(
    private val apiService: ApiService
) {
    fun signIn(
        email: String,
        password: String
    ): Call<SignInResponse> {
        val data = SignInRequest(email, password)
        return apiService.login(data)
    }
}
