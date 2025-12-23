package com.example.randomuserapp.domain.models

import java.util.Date

data class Dob(
    val date: Date = Date(),
    val age: Int = 0
)
