package com.example.storyappsubmission.data.api

import com.example.storyappsubmission.data.request.LoginRequest
import com.example.storyappsubmission.data.request.RegisterRequest
import com.example.storyappsubmission.data.responses.LoginResponse
import com.example.storyappsubmission.data.responses.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
//    @FormUrlEncoded
//    @POST("register")
//    suspend fun register(
//        @Field("name") name: String,
//        @Field("email") email: String,
//        @Field("password") password: String
//    ): RegisterRequest

    @POST("register")
    fun registerUser(
        @Body request: RegisterRequest,
    ): Call<RegisterResponse>

//    @FormUrlEncoded
//    @POST("login")
//    suspend fun login(
//        @Field("email") email: String,
//        @Field("password") password: String
//    ): LoginRequest

    @POST("login")
    fun loginUser(
        @Body request: LoginRequest,
    ): Call<LoginResponse>
}