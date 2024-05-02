package com.example.catprice.retrofit

import com.example.catprice.ui.auth.models.LogOutResponse
import com.example.catprice.ui.auth.models.SignInResponse
import com.example.catprice.ui.auth.models.SignUpResponse
import com.example.catprice.ui.auth.request.SignInRequest
import com.example.catprice.ui.auth.request.SignUpRequest
import com.example.catprice.ui.setting.models.ChangePasswordResponse
import com.example.catprice.ui.setting.models.UserResponse
import com.example.catprice.ui.setting.request.ChangePasswordRequest
import com.example.catprice.ui.setting.request.GetUserRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
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
        @Query("_id") userId: String
    ): Call<LogOutResponse>

    @PUT("api/v1/user/changePassword")
    fun changePass(
        @Header("Authorization") token: String,
        @Body req: ChangePasswordRequest,
        @Query("_id") userId: String
    ): Call<ChangePasswordResponse>

    @GET("api/v1/user/get")
    fun getUser(
        @Header("Authorization") token: String,
        @Query("_id") userId: String
    ): Call<UserResponse>

    @PUT("api/v1/user/update")
    fun updateUser(
        @Header("Authorization") token: String,
        @Query("_id") userId: String,
        @Body req: GetUserRequest
    ): Call<UserResponse>
}