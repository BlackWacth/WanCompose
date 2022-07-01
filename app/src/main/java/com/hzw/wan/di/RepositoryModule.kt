package com.hzw.wan.di

import com.hzw.wan.domain.repository.home.HomeRepository
import com.hzw.wan.domain.repository.home.HomeRepositoryImpl
import com.hzw.wan.domain.repository.system.SystemRepository
import com.hzw.wan.domain.repository.system.SystemRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindHomeRepository(
        homeRepositoryImpl: HomeRepositoryImpl
    ): HomeRepository

    @Binds
    @Singleton
    abstract fun bindSystemRepository(
        systemRepositoryImpl: SystemRepositoryImpl
    ): SystemRepository
}