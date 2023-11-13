package com.example.storyappsubmission.view.login

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyappsubmission.data.api.ApiConfig
import com.example.storyappsubmission.data.repository.Repository
import com.example.storyappsubmission.data.request.LoginRequest
import com.example.storyappsubmission.data.responses.ErrorResponse
import com.example.storyappsubmission.data.responses.LoginResponse
import com.example.storyappsubmission.data.responses.LoginResult
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val repository: Repository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorResponse = MutableLiveData<ErrorResponse>()
    val errorResponse: LiveData<ErrorResponse> = _errorResponse

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    fun loginUser(loginRequest: LoginRequest) {
        _isLoading.value = true
        val apiService = ApiConfig().getApiService()
        val login = apiService.loginUser(loginRequest)

        login.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _loginResponse.value = response.body()
                    }
                } else {
                    val errBody = response.errorBody()
                    val errJsonString = errBody?.string()
                    val gson = Gson()
                    _errorResponse.value = gson.fromJson(errJsonString, ErrorResponse::class.java)
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
                _errorResponse.value = ErrorResponse(null, null)
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun saveSession(loginResult: LoginResult) {
        viewModelScope.launch {
            repository.login(loginResult)
        }
    }
}