package com.hzw.wan.domain.repository.home

import com.hzw.wan.data.WanApi
import com.hzw.wan.domain.Resource
import com.hzw.wan.domain.mapper.toArticle
import com.hzw.wan.domain.mapper.toBanner
import com.hzw.wan.domain.model.Article
import com.hzw.wan.domain.model.Banner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepositoryImpl @Inject constructor(
    private val wanApi: WanApi
) : HomeRepository {

    override suspend fun getBanner(): Flow<Resource<List<Banner>>> {
        return flow {
            emit(Resource.Loading(true))
            val bannerList = try {
                val dto = wanApi.getBanner()
                dto.data?.map {
                    it.toBanner()
                }
            } catch (e: Exception) {
                emit(Resource.Error("接口加载异常"))
                null
            }
            emit(Resource.Success(bannerList))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getArticle(a: Int): Flow<Resource<List<Article>>> {
        return flow {
            emit(Resource.Loading(false))
            try {
                val list = wanApi.getArticle(a).data?.datas?.map {
                    it.toArticle()
                }
                if (list != null) {
                    emit(Resource.Success(list))
                } else {
                    emit(Resource.Error("获取文章接口异常"))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error("获取文章接口异常"))
            } finally {
                emit(Resource.Loading(false))
            }
        }
    }
}