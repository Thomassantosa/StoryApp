package com.example.storyappsubmission.data.repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.storyappsubmission.data.api.ApiService
import com.example.storyappsubmission.data.pagingsource.StoryPagingSource
import com.example.storyappsubmission.data.pref.UserPreference
import com.example.storyappsubmission.data.responses.ListStoryItem
import com.example.storyappsubmission.data.responses.LocationResponse
import com.example.storyappsubmission.data.responses.LoginResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(private val apiService: ApiService, private val userPreference: UserPreference) {

    suspend fun login(loginResult: LoginResult) {
        userPreference.login(loginResult)
    }

    fun getUser(): LiveData<LoginResult> {
        return userPreference.getUser().asLiveData()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun getStoryData(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, userPreference.getToken())
            }
        ).liveData
    }

    fun getStoryLocation(token: String): LiveData<LocationResponse> {
        val storyMapResponseLiveData = MutableLiveData<LocationResponse>()

        val client = apiService.storyLocation("Bearer $token", "1")
        client.enqueue(object : Callback<LocationResponse> {
            override fun onResponse(
                call: Call<LocationResponse>,
                response: Response<LocationResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        storyMapResponseLiveData.value = response.body()
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LocationResponse>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })

        return storyMapResponseLiveData
    }
}