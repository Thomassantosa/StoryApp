package com.example.storyappsubmission.data.api

import com.example.storyappsubmission.data.request.LoginRequest
import com.example.storyappsubmission.data.request.RegisterRequest
import com.example.storyappsubmission.data.responses.ErrorResponse
import com.example.storyappsubmission.data.responses.LocationResponse
import com.example.storyappsubmission.data.responses.LoginResponse
import com.example.storyappsubmission.data.responses.RegisterResponse
import com.example.storyappsubmission.data.responses.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    @POST("register")
    fun registerUser(
        @Body request: RegisterRequest,
    ): Call<RegisterResponse>

    @POST("login")
    fun loginUser(
        @Body request: LoginRequest,
    ): Call<LoginResponse>

    @GET("stories")
    fun getStories(
        @Header("Authorization") token: String
    ): Call<StoryResponse>

    @Multipart
    @POST("stories")
    fun uploadImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<ErrorResponse>

    @GET("/v1/stories")
    fun storyLocation(
        @Header("Authorization") token: String,
        @Query("location") queryParam: String
    ): Call<LocationResponse>
}