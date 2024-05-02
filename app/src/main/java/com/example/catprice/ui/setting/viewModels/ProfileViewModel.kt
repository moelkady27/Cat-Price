package com.example.catprice.ui.setting.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catprice.ui.setting.models.UserResponse
import com.example.catprice.ui.setting.repository.ProfileRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(
    private val profileRepository: ProfileRepository
): ViewModel() {

    val userViewModelLiveData: MutableLiveData<UserResponse> = MutableLiveData()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()

    fun getUser(token: String, userId: String) {

        profileRepository.getUser(token, userId)
            .enqueue(object : Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        userViewModelLiveData.value = response.body()
                    } else {
                        errorLiveData.value = response.errorBody()?.string()
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    errorLiveData.value = t.message
                }

            })
    }

    fun updateUser(token: String, userId: String, name: String, phoneNumber: String, email: String, type: String) {
        profileRepository.updateUser(token, userId, name, phoneNumber, email, type)
            .enqueue(object : Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        userViewModelLiveData.value = response.body()
                    } else {
                        errorLiveData.value = response.errorBody()?.string()
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    errorLiveData.value = t.message
                }

            })
    }

}