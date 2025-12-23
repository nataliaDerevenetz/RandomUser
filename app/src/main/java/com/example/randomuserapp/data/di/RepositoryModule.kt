package com.example.randomuserapp.data.di

import com.example.randomuserapp.data.repository.RandomUserRepositoryImpl
import com.example.randomuserapp.domain.repository.RandomUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModuleBinder {
    @Singleton
    @Binds
    abstract fun bindRandomUserRepository(
        weatherRepositoryImpl: RandomUserRepositoryImpl
    ): RandomUserRepository
}