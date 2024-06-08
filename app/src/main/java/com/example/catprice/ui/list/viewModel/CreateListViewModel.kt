package com.example.catprice.ui.list.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catprice.ui.list.models.CreateListResponse
import com.example.catprice.ui.list.repository.CreateListRepository
import okio.IOException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateListViewModel(
    private val createListRepository: CreateListRepository
): ViewModel(){

    val createListLiveData: MutableLiveData<CreateListResponse> = MutableLiveData()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()

    fun createList(token: String, userId: String, listName: String){
        createListRepository.createList("Bearer $token", userId, listName)
            .enqueue(object: Callback<CreateListResponse>{
                override fun onResponse(
                    call: Call<CreateListResponse>,
                    response: Response<CreateListResponse>
                ) {
                    if(response.isSuccessful){
                        createListLiveData.value = response.body()
                    }else{
                        if (response.code() in 400..499) {
                            response.errorBody()?.let {
                                try {
                                    val errorMessage = JSONObject(it.string()).getString("message")
                                    errorLiveData.value = errorMessage
                                } catch (e: JSONException) {
                                    errorLiveData.value = "Error parsing error body"
                                } catch (e: IOException) {
                                    errorLiveData.value = "Error reading response body"
                                }
                            }
                        } else {
                            errorLiveData.value = "Server error"
                        }
                    }
                }

                override fun onFailure(call: Call<CreateListResponse>, t: Throwable) {
                    errorLiveData.value = t.message
                }

            })
    }
}