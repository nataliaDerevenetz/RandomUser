package com.example.randomuserapp.data.local

import com.example.randomuserapp.data.local.dao.UserDao
import com.example.randomuserapp.data.mappers.toUser
import com.example.randomuserapp.data.mappers.toUserEntity
import com.example.randomuserapp.domain.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DBStorageImpl @Inject constructor(
    private val userDao: UserDao,
    ) : DBStorage
{
    override suspend fun insertUser(user: User) {
        userDao.insert(user.toUserEntity())
    }

    override fun getUsers(): Flow<List<User>> {
        return userDao.getUsers().map {
            it.map{ it1 -> it1.toUser()} }
    }

    override suspend fun deleteUser(user: User) {
        userDao.delete(user.toUserEntity())
    }

    override fun getUserById(id: String): Flow<User> {
       return userDao.getUserById(id).map { it.toUser() }
    }

}