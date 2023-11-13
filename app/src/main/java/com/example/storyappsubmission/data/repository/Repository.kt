package com.example.storyappsubmission.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.storyappsubmission.data.api.ApiService
import com.example.storyappsubmission.data.pref.UserPreference
import com.example.storyappsubmission.data.responses.LoginResult

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
}