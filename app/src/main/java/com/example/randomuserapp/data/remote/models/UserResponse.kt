package com.example.randomuserapp.data.remote.models

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("results")
    val results: List<ResultsResponse>,
)
