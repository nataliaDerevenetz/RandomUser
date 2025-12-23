package com.example.randomuserapp.data.remote.models

import com.google.gson.annotations.SerializedName

data class DobResponse(
    @SerializedName("date")
    val date: String,

    @SerializedName("age")
    val age: Int,
)
