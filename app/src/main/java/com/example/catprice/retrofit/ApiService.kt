package com.example.catprice.retrofit

import com.example.catprice.ui.auth.models.SignUpResponse
import com.example.catprice.ui.auth.request.SignUpRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("api/v1/user/register")
    fun register(
        @Body req: SignUpRequest
    ): Call<SignUpResponse>
}