package com.example.catprice.ui.auth.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catprice.retrofit.RetrofitClient
import com.example.catprice.ui.auth.models.SignInResponse
import com.example.catprice.ui.auth.request.SignInRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInViewModel: ViewModel() {

    val signInResponseLiveData: MutableLiveData<SignInResponse> = MutableLiveData()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()

    fun signIn(email: String, password: String) {
        val data = SignInRequest(email, password)

        RetrofitClient.instance.login(data)
            .enqueue(object : Callback<SignInResponse> {
                override fun onResponse(
                    call: Call<SignInResponse>,
                    response: Response<SignInResponse>
                ) {
                    if (response.isSuccessful) {
                        signInResponseLiveData.value = response.body()
                    } else {
                        errorLiveData.value = response.errorBody()?.string()
                    }
                }

                override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                    errorLiveData.value = t.message
                }
            })
    }
}