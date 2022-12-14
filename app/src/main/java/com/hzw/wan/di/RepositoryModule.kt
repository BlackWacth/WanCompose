package com.hzw.wan.di

import com.hzw.wan.domain.repository.account.AccountRepository
import com.hzw.wan.domain.repository.account.AccountRepositoryImpl
import com.hzw.wan.domain.repository.course.CourseRepository
import com.hzw.wan.domain.repository.course.CourseRepositoryImpl
import com.hzw.wan.domain.repository.home.HomeRepository
import com.hzw.wan.domain.repository.home.HomeRepositoryImpl
import com.hzw.wan.domain.repository.project.ProjectRepository
import com.hzw.wan.domain.repository.project.ProjectRepositoryImpl
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
    abstract fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @Binds
    @Singleton
    abstract fun bindSystemRepository(systemRepositoryImpl: SystemRepositoryImpl): SystemRepository

    @Binds
    @Singleton
    abstract fun bindProjectRepository(repository: ProjectRepositoryImpl): ProjectRepository

    @Binds
    @Singleton
    abstract fun bindCourseRepository(repository: CourseRepositoryImpl): CourseRepository

    @Binds
    @Singleton
    abstract fun bindAccountRepository(repository: AccountRepositoryImpl): AccountRepository
}