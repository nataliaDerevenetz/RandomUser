package com.example.randomuserapp.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @ColumnInfo val gender: String,
    @ColumnInfo val title: String,
    @ColumnInfo val first: String,
    @ColumnInfo val last: String,
    @ColumnInfo val street_number: Int,
    @ColumnInfo val street_name: String,
    @ColumnInfo val city: String,
    @ColumnInfo val state: String,
    @ColumnInfo val country: String,
    @ColumnInfo val postcode: String,
    @ColumnInfo val coordinates_latitude: String,
    @ColumnInfo val coordinates_longitude: String,
    @ColumnInfo val email: String,
    @ColumnInfo val username: String,
    @ColumnInfo val dob: String,
    @ColumnInfo val age: Int,
    @ColumnInfo val phone: String,
    @ColumnInfo val cell: String,
    @ColumnInfo val picture: String,
    @ColumnInfo val nat: String,
    )



