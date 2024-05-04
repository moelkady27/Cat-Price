package com.example.catprice.ui.setting.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catprice.ui.setting.models.ContactUsResponse
import com.example.catprice.ui.setting.repository.ContactUsRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactUsViewModel(
    private val contactUsRepository: ContactUsRepository
): ViewModel() {

    val contactUsLiveData: MutableLiveData<ContactUsResponse> = MutableLiveData()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()

    fun contactUs(token: String, userId: String, message: String) {
        contactUsRepository.contactUs(token, userId, message)
            .enqueue(object : Callback<ContactUsResponse>{
                override fun onResponse(
                    call: Call<ContactUsResponse>,
                    response: Response<ContactUsResponse>
                ) {
                    if (response.isSuccessful) {
                        contactUsLiveData.value = response.body()
                    } else {
                        errorLiveData.value = response.errorBody()?.string()
                    }
                }

                override fun onFailure(call: Call<ContactUsResponse>, t: Throwable) {
                    errorLiveData.value = t.message
                }

            })
    }
}