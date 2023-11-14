package com.example.storyappsubmission.view.main

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyappsubmission.data.api.ApiConfig
import com.example.storyappsubmission.data.repository.Repository
import com.example.storyappsubmission.data.responses.ErrorResponse
import com.example.storyappsubmission.data.responses.LoginResult
import com.example.storyappsubmission.data.responses.StoryResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val _errorResponse = MutableLiveData<ErrorResponse>()
    val errorResponse: LiveData<ErrorResponse> = _errorResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listStory = MutableLiveData<StoryResponse>()
    val listStory: LiveData<StoryResponse> = _listStory

    fun getStory(token : String) {
        _isLoading.value = true
        val apiService = ApiConfig().getApiService()
        val storyResponses = apiService.getStories("Bearer $token")

        storyResponses.enqueue(object : Callback<StoryResponse> {
            override fun onResponse(
                call: Call<StoryResponse>,
                response: Response<StoryResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listStory.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getUser(): LiveData<LoginResult> {
        return repository.getUser()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}