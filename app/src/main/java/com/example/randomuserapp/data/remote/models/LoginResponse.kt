package com.example.randomuserapp.data.remote.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("uuid")
    val uuid: String,

    @SerializedName("username")
    val username: String,
)
