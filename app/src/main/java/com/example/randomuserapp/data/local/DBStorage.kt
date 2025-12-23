package com.example.randomuserapp.data.local

import com.example.randomuserapp.domain.models.User
import kotlinx.coroutines.flow.Flow

interface DBStorage {
    suspend fun insertUser(user: User)
    fun getUsers(): Flow<List<User>>
    suspend fun deleteUser(user: User)
    fun getUserById(id: String): Flow<User>
}