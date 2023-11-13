package com.example.storyappsubmission.data.api

import com.example.storyappsubmission.data.request.LoginRequest
import com.example.storyappsubmission.data.request.RegisterRequest
import com.example.storyappsubmission.data.responses.LoginResponse
import com.example.storyappsubmission.data.responses.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("register")
    fun registerUser(
        @Body request: RegisterRequest,
    ): Call<RegisterResponse>

    @POST("login")
    fun loginUser(
        @Body request: LoginRequest,
    ): Call<LoginResponse>
}