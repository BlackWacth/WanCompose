package com.hzw.wan.domain.repository.home

import com.hzw.wan.data.Api
import com.hzw.wan.domain.Resource
import com.hzw.wan.domain.mapper.toBanner
import com.hzw.wan.domain.model.Article
import com.hzw.wan.domain.model.Banner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeRepositoryImpl(
    private val api: Api
) : HomeRepository {

    override suspend fun getBanner(): Flow<Resource<List<Banner>>> {
        return flow {
            emit(Resource.Loading(true))
            val bannerList = try {
                val dto = api.getBanner()
                dto.data?.map {
                    it.toBanner()
                }
            } catch (e: Exception) {
                emit(Resource.Error("接口加载异常"))
                null
            }
            emit(Resource.Success(bannerList))
        }
    }

    override suspend fun getArticle(a: Int): Flow<Resource<List<Article>>> {
        TODO("Not yet implemented")
    }
}