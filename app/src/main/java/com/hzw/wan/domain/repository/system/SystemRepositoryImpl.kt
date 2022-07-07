package com.hzw.wan.domain.repository.system

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hzw.wan.data.PAGE_SIZE
import com.hzw.wan.data.WanApi
import com.hzw.wan.data.dto.checkSuccess
import com.hzw.wan.domain.Resource
import com.hzw.wan.domain.mapper.toAndroidSystem
import com.hzw.wan.domain.model.AndroidSystem
import com.hzw.wan.domain.model.Article
import com.hzw.wan.domain.repository.paging.SystemArticlePageSource
import com.hzw.wan.extend.logE
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SystemRepositoryImpl @Inject constructor(private val wanApi: WanApi) : SystemRepository {
    override suspend fun getSystem(): Flow<Resource<List<AndroidSystem>>> {
        return flow {
            emit(Resource.Loading(true))
            val list = wanApi.getAndroidSystem().checkSuccess()?.map {
                it.toAndroidSystem()
            } ?: listOf()
            emit(Resource.Success(list))
        }.catch {
            "获取体系数据异常".logE(throwable = it)
            emit(Resource.Error("获取体系数据异常:${it.message}"))
        }
    }

    override suspend fun getSystemArticleList(id: Int): Flow<PagingData<Article>> {
        return Pager(config = PagingConfig(PAGE_SIZE)) {
            SystemArticlePageSource(wanApi, id)
        }.flow
    }
}