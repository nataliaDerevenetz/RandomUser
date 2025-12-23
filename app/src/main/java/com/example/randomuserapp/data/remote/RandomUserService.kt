package com.example.randomuserapp.data.remote

import com.example.randomuserapp.data.remote.models.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserService {
    @GET("api/")
    suspend fun createUser(@Query("gender") gender:String, @Query("nat") nat:String): Response<UserResponse>
}