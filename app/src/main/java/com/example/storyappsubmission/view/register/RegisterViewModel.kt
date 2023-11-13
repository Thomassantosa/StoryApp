package com.example.storyappsubmission.view.register

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyappsubmission.data.api.ApiConfig
import com.example.storyappsubmission.data.repository.Repository
import com.example.storyappsubmission.data.request.RegisterRequest
import com.example.storyappsubmission.data.responses.ErrorResponse
import com.example.storyappsubmission.data.responses.RegisterResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val repository: Repository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorResponse = MutableLiveData<ErrorResponse>()
    val errorResponse: LiveData<ErrorResponse> = _errorResponse

    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    fun registerUser(registerRequest : RegisterRequest){
        _isLoading.value = true
        val apiService = ApiConfig().getApiService()
        val register = apiService.registerUser(registerRequest)

        register.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        _registerResponse.value = response.body()
                    }
                }else {
                    val errBody = response.errorBody()
                    val errJsonString = errBody?.string()
                    val gson = Gson()
                    _errorResponse.value = gson.fromJson(errJsonString, ErrorResponse::class.java)
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
                _errorResponse.value = ErrorResponse(null, null)
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}