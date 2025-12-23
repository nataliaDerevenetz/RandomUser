package com.example.randomuserapp.data.di

import android.content.Context
import androidx.room.Room
import com.example.randomuserapp.data.local.db.RandomUserRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModuleProvider {

    @Provides
    fun provideUserDao(database: RandomUserRoomDatabase) = database.userDao()

    @Provides
    @Singleton
    fun providesLocalDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        RandomUserRoomDatabase::class.java,
        "usersDB"
    ).build()
}