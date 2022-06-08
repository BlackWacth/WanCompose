package com.hzw.wan.domain.repository.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hzw.wan.data.PAGE_SIZE
import com.hzw.wan.data.WanApi
import com.hzw.wan.data.dto.checkSuccess
import com.hzw.wan.domain.mapper.toBanner
import com.hzw.wan.domain.model.Article
import com.hzw.wan.domain.model.Banner
import com.hzw.wan.domain.repository.paging.HomePageSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepositoryImpl @Inject constructor(
    private val wanApi: WanApi
) : HomeRepository {

    override suspend fun getBanner(): List<Banner> {
        return try {
            wanApi.getBanner().checkSuccess()?.map {
                it.toBanner()
            } ?: listOf()
        } catch (e: Exception) {
            e.printStackTrace()
            listOf()
        }
    }

    override suspend fun getArticleList(index: Int): Flow<PagingData<Article>> {
        return Pager(config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = true, initialLoadSize = PAGE_SIZE)) {
            HomePageSource(wanApi)
        }.flow
    }
}