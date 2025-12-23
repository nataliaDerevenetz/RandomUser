package com.example.randomuserapp.data.remote.models

import com.google.gson.annotations.SerializedName

data class NameResponse(
    @SerializedName("title")
    val title: String,

    @SerializedName("first")
    val first: String,

    @SerializedName("last")
    val last: String,
)
