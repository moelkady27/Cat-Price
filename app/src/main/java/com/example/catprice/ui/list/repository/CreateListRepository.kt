package com.example.catprice.ui.list.repository

import com.example.catprice.retrofit.ApiService
import com.example.catprice.ui.list.models.CreateListResponse
import com.example.catprice.ui.list.request.CreateListRequest
import retrofit2.Call

class CreateListRepository(
    private val apiService: ApiService
) {

    fun createList(
        token: String,
        userId: String,
        listName: String
    ): Call<CreateListResponse>{
        val data = CreateListRequest(userId, listName)
        return apiService.createList(token, data)
    }
}