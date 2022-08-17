package com.hzw.wan.domain.repository.account

import com.hzw.wan.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun login(username: String, password: String): Flow<UserInfo>

    suspend fun register(username: String, password: String, rePassword: String): Flow<UserInfo>

    suspend fun logout(): Flow<Any>

}