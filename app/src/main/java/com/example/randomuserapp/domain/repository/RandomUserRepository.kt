package com.example.randomuserapp.domain.repository

import com.example.randomuserapp.domain.models.User
import com.example.randomuserapp.utils.UIResources
import kotlinx.coroutines.flow.Flow

interface RandomUserRepository {
    suspend fun createUser(gender: String, nationality: String): Flow<UIResources<User>>
    fun getUsersFromDB(): Flow<List<User>>
    suspend fun deleteUser(user: User)
    fun getUserById(id: String): Flow<User>
}