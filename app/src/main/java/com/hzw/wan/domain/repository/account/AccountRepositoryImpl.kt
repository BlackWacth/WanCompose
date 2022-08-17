package com.hzw.wan.domain.repository.account

import com.hzw.wan.data.WanApi
import com.hzw.wan.data.dto.checkSuccess
import com.hzw.wan.domain.mapper.toUserInfo
import com.hzw.wan.domain.model.UserInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepositoryImpl @Inject constructor(private val wanApi: WanApi) : AccountRepository {
    override suspend fun login(username: String, password: String): Flow<UserInfo> {
        return flow {
            delay(3000)
            val userInfo = wanApi.login(username, password).checkSuccess()?.toUserInfo()
            checkNotNull(userInfo) {
                "登录返回数据为空"
            }
            emit(userInfo)
        }
    }

    override suspend fun register(
        username: String,
        password: String,
        rePassword: String
    ): Flow<UserInfo> {
        return flow {
            val userInfo =
                wanApi.register(username, password, rePassword).checkSuccess()?.toUserInfo()
            checkNotNull(userInfo) {
                "登录返回数据为空"
            }
            emit(userInfo)
        }
    }

    override suspend fun logout(): Flow<Any> {
        return flow {
            val data = wanApi.logout().checkSuccess()
            emit(data ?: "")
        }
    }
}