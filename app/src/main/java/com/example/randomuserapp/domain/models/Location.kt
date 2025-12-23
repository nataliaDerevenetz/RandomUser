package com.example.randomuserapp.domain.models

data class Location(
    val city: String = "",
    val street: Street = Street(),
    val country: String = "",
    val postcode: String = "",
    val state: String = "",
    val coordinates: Coordinates = Coordinates()
)
