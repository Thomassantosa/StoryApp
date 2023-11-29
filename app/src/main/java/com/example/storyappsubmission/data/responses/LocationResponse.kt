package com.example.storyappsubmission.data.responses

import com.google.gson.annotations.SerializedName

data class LocationResponse(

    @field:SerializedName("listStory")
    val listStory: List<ListStoryLocationItem>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class ListStoryLocationItem(

    @field:SerializedName("photoUrl")
    val photoUrl: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("lon")
    val lon: Any,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("lat")
    val lat: Any
)