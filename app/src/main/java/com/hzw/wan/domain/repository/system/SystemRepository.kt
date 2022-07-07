package com.hzw.wan.domain.repository.system

import androidx.paging.PagingData
import com.hzw.wan.domain.Resource
import com.hzw.wan.domain.model.AndroidSystem
import com.hzw.wan.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface SystemRepository {

    suspend fun getSystem(): Flow<Resource<List<AndroidSystem>>>

    suspend fun getSystemArticleList(id: Int): Flow<PagingData<Article>>
}