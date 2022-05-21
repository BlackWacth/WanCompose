package com.hzw.wan.domain.repository.home

import com.hzw.wan.domain.Resource
import com.hzw.wan.domain.model.Article
import com.hzw.wan.domain.model.Banner
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun getBanner(): Flow<Resource<List<Banner>>>

    suspend fun getArticle(a: Int): Flow<Resource<List<Article>>>

}