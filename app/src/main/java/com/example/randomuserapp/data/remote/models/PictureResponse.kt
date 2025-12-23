package com.example.randomuserapp.data.remote.models

import com.google.gson.annotations.SerializedName

data class PictureResponse(
    @SerializedName("large")
    val large: String,
)
