package com.example.randomuserapp.data.remote.models

import com.google.gson.annotations.SerializedName

data class ResultsResponse(
    @SerializedName("gender")
    val gender: String,

    @SerializedName("name")
    val name: NameResponse,

    @SerializedName("location")
    val location: LocationResponse,

    @SerializedName("email")
    val email: String,

    @SerializedName("login")
    val login: LoginResponse,

    @SerializedName("dob")
    val dob: DobResponse,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("cell")
    val cell: String,

    @SerializedName("picture")
    val picture: PictureResponse,

    @SerializedName("nat")
    val nat: String,
    )
