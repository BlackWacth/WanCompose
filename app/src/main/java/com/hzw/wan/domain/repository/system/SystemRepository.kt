package com.hzw.wan.domain.repository.system

import com.hzw.wan.domain.Resource
import com.hzw.wan.domain.model.AndroidSystem
import kotlinx.coroutines.flow.Flow

interface SystemRepository {

    suspend fun getSystem(): Flow<Resource<List<AndroidSystem>>>

}