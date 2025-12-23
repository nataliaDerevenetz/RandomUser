package com.example.randomuserapp.domain.models


data class User(
    val id:String = "",
    val name: Name = Name(),
    val gender: String = "",
    val location: Location = Location(),
    val email: String = "",
    val username: String = "",
    val dob: Dob = Dob(),
    val phone: String = "",
    val cell: String = "",
    val picture: String = "",
    val nat: String = ""
)
