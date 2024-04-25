package com.example.catprice.ui.auth.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catprice.ui.auth.models.SignUpResponse
import com.example.catprice.ui.auth.repository.SignUpRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel(
    private val signUpRepository: SignUpRepository
): ViewModel() {

    val signUpResponseLiveData: MutableLiveData<SignUpResponse> = MutableLiveData()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()

    fun register(name: String, email: String, password: String, country: String, phoneNumber: String, type: String) {

        signUpRepository.signUp(name, email, password, country, phoneNumber, type)
            .enqueue(object : Callback<SignUpResponse> {
                override fun onResponse(
                    call: Call<SignUpResponse>,
                    response: Response<SignUpResponse>
                ) {
                    if (response.isSuccessful) {
                        signUpResponseLiveData.value = response.body()
                    } else {
                        errorLiveData.value = response.errorBody()?.string()
                    }
                }

                override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                    errorLiveData.value = t.message
                }
            })
    }
}