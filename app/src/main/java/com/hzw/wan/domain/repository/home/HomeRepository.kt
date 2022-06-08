package com.hzw.wan.domain.repository.home

import androidx.paging.PagingData
import com.hzw.wan.domain.model.Article
import com.hzw.wan.domain.model.Banner
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun getBanner(): List<Banner>

    suspend fun getArticleList(index: Int): Flow<PagingData<Article>>

}