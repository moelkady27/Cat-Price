package com.example.catprice.ui.setting.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catprice.retrofit.RetrofitClient
import com.example.catprice.ui.setting.models.ChangePasswordResponse
import com.example.catprice.ui.setting.request.ChangePasswordRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordViewModels: ViewModel() {

    val changePassResponseLiveData: MutableLiveData<ChangePasswordResponse> = MutableLiveData()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()

    fun changePass(token: String, currentPassword: String, newPassword: String, confirmPassword: String, userId: String) {
        val data = ChangePasswordRequest(currentPassword, newPassword, confirmPassword)

        RetrofitClient.instance.changePass( "Bearer $token", data, userId)
            .enqueue(object : Callback<ChangePasswordResponse> {
                override fun onResponse(
                    call: Call<ChangePasswordResponse>,
                    response: Response<ChangePasswordResponse>
                ) {
                    if (response.isSuccessful) {
                        changePassResponseLiveData.value = response.body()
                    } else {
                        errorLiveData.value = response.errorBody()?.string()
                    }
                }

                override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                    errorLiveData.value = t.message
                }
            })
    }
}