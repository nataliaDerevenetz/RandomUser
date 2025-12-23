package com.example.randomuserapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.randomuserapp.data.local.dao.UserDao
import com.example.randomuserapp.data.local.models.UserEntity

@Database(entities = [UserEntity::class], version = 1,exportSchema = false)
abstract class RandomUserRoomDatabase: RoomDatabase() {
    abstract fun userDao() : UserDao
}