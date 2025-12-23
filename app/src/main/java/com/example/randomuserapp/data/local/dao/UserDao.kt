package com.example.randomuserapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.randomuserapp.data.local.models.UserEntity
import com.example.randomuserapp.domain.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity)

    @Query("SELECT * FROM userEntity")
    fun getUsers() : Flow<List<UserEntity>>

    @Delete
    suspend fun delete(user: UserEntity)

    @Query("SELECT * FROM userEntity WHERE id = :id LIMIT 1")
    fun getUserById(id: String) : Flow<UserEntity>

}