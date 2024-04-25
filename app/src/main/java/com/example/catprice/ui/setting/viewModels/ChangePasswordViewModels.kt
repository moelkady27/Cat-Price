package com.example.catprice.ui.setting.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catprice.ui.setting.models.ChangePasswordResponse
import com.example.catprice.ui.setting.repository.ChangePasswordRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordViewModels(
    private val changePasswordRepository: ChangePasswordRepository
): ViewModel() {

    val changePassResponseLiveData = MutableLiveData<ChangePasswordResponse>()
    val errorLiveData = MutableLiveData<String>()

    fun changePass(token: String, currentPassword: String, newPassword: String, confirmPassword: String, userId: String){

        changePasswordRepository.changePass(token, currentPassword, newPassword, confirmPassword, userId)
            .enqueue(object : Callback<ChangePasswordResponse>{
                override fun onResponse(call: Call<ChangePasswordResponse>, response: Response<ChangePasswordResponse>) {
                    if (response.isSuccessful){
                        changePassResponseLiveData.postValue(response.body())
                    }else{
                        errorLiveData.postValue(response.errorBody()?.string())
                    }
                }

                override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                    errorLiveData.postValue(t.message)
                }
            })
    }
}