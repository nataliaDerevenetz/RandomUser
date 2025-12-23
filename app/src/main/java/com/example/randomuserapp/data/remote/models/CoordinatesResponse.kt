package com.example.randomuserapp.data.remote.models

import com.google.gson.annotations.SerializedName

data class CoordinatesResponse(
    @SerializedName("latitude")
    val latitude: String,

    @SerializedName("longitude")
    val longitude: String,
)
