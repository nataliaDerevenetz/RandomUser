package com.example.randomuserapp.data.repository

import com.example.randomuserapp.data.local.DBStorage
import com.example.randomuserapp.data.mappers.toUser
import com.example.randomuserapp.data.remote.RandomUserService
import com.example.randomuserapp.domain.repository.RandomUserRepository
import com.example.randomuserapp.domain.models.User
import com.example.randomuserapp.utils.UIResources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RandomUserRepositoryImpl @Inject constructor(
    private val userService: RandomUserService,
    private val dbStorage: DBStorage,
): RandomUserRepository {

    override suspend fun createUser(gender: String, nationality: String): Flow<UIResources<User>> = flow {
        try {
            emit(UIResources.Loading)
            val result = userService.createUser(gender, nationality)
            if (result.isSuccessful) {
                val user = result.body()?.toUser() ?: User()
                dbStorage.insertUser(user)
                emit(UIResources.Success( user))
            } else {
                  emit(UIResources.Error("Failed to insert user"))
            }
        } catch (e: Exception) {
            emit(UIResources.Error(e.message.toString()))
        }
    }

    override fun getUsersFromDB(): Flow<List<User>> {
        return dbStorage.getUsers().flowOn(Dispatchers.IO)
    }

    override suspend fun deleteUser(user: User) {
        dbStorage.deleteUser(user)
    }

    override fun getUserById(id: String): Flow<User> {
        return dbStorage.getUserById(id).flowOn(Dispatchers.IO)
    }
}