package com.hzw.wan.domain.repository.account

interface AccountRepository {

    suspend fun login(username: String, password: String)

    suspend fun register(username: String, password: String, rePassword: String)

    suspend fun logout()

}