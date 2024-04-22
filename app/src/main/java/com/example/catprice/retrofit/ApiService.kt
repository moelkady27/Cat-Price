package com.example.catprice.retrofit

import com.example.catprice.ui.auth.models.LogOutResponse
import com.example.catprice.ui.auth.models.SignInResponse
import com.example.catprice.ui.auth.models.SignUpResponse
import com.example.catprice.ui.auth.request.SignInRequest
import com.example.catprice.ui.auth.request.SignUpRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("api/v1/user/register")
    fun register(
        @Body req: SignUpRequest
    ): Call<SignUpResponse>

    @POST("api/v1/user/login")
    fun login(
        @Body req: SignInRequest
    ): Call<SignInResponse>

    @PUT("api/v1/user/logout")
    fun logOut(
        @Header("Authorization") token: String,
        @Query("_id") userId: String,
    ): Call<LogOutResponse>

}